"""Validate multi-file/classpath/JAR lessons, including intentional failure stages."""
from pathlib import Path
import hashlib
import json
import os
import re
import shutil
import sys
import uuid
import zipfile

from verify_course import ROOT, find_jdk, invoke

def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()

def main():
    if hasattr(sys.stdout, 'reconfigure'):
        sys.stdout.reconfigure(encoding='utf-8')
    manifest=ROOT/'_课程工具/projects.json'
    projects=json.loads(manifest.read_text(encoding='utf-8'))
    jdk,suffix=find_jdk()
    compiler=str(jdk/'bin'/('javac'+suffix))
    launcher=str(jdk/'bin'/('java'+suffix))
    archiver=str(jdk/'bin'/('jar'+suffix))
    build=ROOT/'.build'/('projects-verify-'+uuid.uuid4().hex)
    build.mkdir(parents=True)
    results=[]
    source_hashes={}
    lecture_hashes={}
    def run(command,cwd,expected=None,failed_marker=None):
        result=invoke(command,cwd)
        if failed_marker:
            assert result['exit_code']!=0 and failed_marker in result['stderr'],result
        else:
            assert result['exit_code']==0,result
            if expected is not None:assert result['stdout']==expected,result
        return result
    options=['-Dfile.encoding=UTF-8','-Dstdout.encoding=UTF-8','-Dstderr.encoding=UTF-8','-Duser.language=en']
    compile_options=['-J-Dfile.encoding=UTF-8','--release','8','-encoding','UTF-8','-Xlint:all,-options']
    for project in projects:
        source=ROOT/project['root']
        work=build/project['id']
        work.mkdir()
        classes=work/'classes';classes.mkdir()
        lecture=source.parents[1]/'讲义.md'
        lecture_hashes[lecture.relative_to(ROOT).as_posix()]=digest(lecture)
        blocks=re.findall(r'<!-- source: code/project/(.*?) -->\n```java-source\n(.*?)\n```',lecture.read_text(encoding='utf-8'),re.S)
        assert {rel for rel,_ in blocks}==set(project['sources']+project['library_sources'])
        for rel,code in blocks:
            assert (source/rel).read_text(encoding='utf-8')==code+'\n',rel
        actual_sources={p.relative_to(source).as_posix() for p in source.rglob('*.java')}
        assert actual_sources==set(project['sources']+project['library_sources'])
        for rel in project['sources']+project['library_sources']+[r['source'] for r in project['resources']]:
            path=source/rel
            assert path.resolve().is_relative_to(source.resolve())
            source_hashes[path.relative_to(ROOT).as_posix()]=digest(path)
        library=None
        if project['library_sources']:
            lib_classes=work/'library-classes';lib_classes.mkdir()
            compiled=run([compiler,*compile_options,'-classpath',str(lib_classes),'-d',str(lib_classes),*[str(source/p) for p in project['library_sources']]],work)
            assert not compiled['stderr'].strip(),compiled
            (work/'lib').mkdir()
            library=work/'lib/labels.jar'
            run([archiver,'cf',str(library),'-C',str(lib_classes),'.'],work)
        compiled=run([compiler,*compile_options,'-classpath',str(library or classes),'-d',str(classes),*[str(source/p) for p in project['sources']]],work)
        assert not compiled['stderr'].strip(),compiled
        for resource in project['resources']:
            target=classes/resource['target'];target.parent.mkdir(parents=True,exist_ok=True)
            shutil.copy2(source/resource['source'],target)
        runtime_cp=os.pathsep.join(str(x) for x in [classes,library] if x)
        cases=[{'args':[],'stdout':project['stdout']},*project['cases']]
        stages=[]
        for case in cases:
            stages.append({'name':'classpath launch','result':run([launcher,*options,'-cp',runtime_cp,project['main'],*case['args']],work,expected=case['stdout'])})
        mf=work/'manifest.mf'
        text='Manifest-Version: 1.0\nMain-Class: '+project['main']+'\n'
        if library:text+='Class-Path: lib/labels.jar\n'
        mf.write_text(text+'\n',encoding='utf-8')
        application=work/'app.jar'
        run([archiver,'cfm',str(application),str(mf),'-C',str(classes),'.'],work)
        with zipfile.ZipFile(application) as z:
            entries=z.namelist()
            assert project['main'].replace('.','/')+'.class' in entries
            assert all(not name.endswith('.java') for name in entries)
            assert all(r['target'] in entries for r in project['resources'])
            assert 'Main-Class: '+project['main'] in z.read('META-INF/MANIFEST.MF').decode()
        for case in cases:
            stages.append({'name':'JAR launch','result':run([launcher,*options,'-jar',str(application),*case['args']],work,expected=case['stdout'])})
        # A different working directory and a copied release must work without the source tree.
        release=work/'release elsewhere';release.mkdir()
        elsewhere=work/'empty cwd';elsewhere.mkdir()
        shutil.copy2(application,release/'app.jar')
        if library:
            (release/'lib').mkdir();shutil.copy2(library,release/'lib/labels.jar')
        stages.append({'name':'relocated release and working directory','result':run([launcher,*options,'-jar',str(release/'app.jar')],elsewhere,expected=project['stdout'])})
        stages.append({'name':'wrong classpath root: expected launch failure','result':run([launcher,*options,'-cp',str(classes/'campus'),project['main']],work,failed_marker='Could not find or load main class')})
        plain=work/'no-entry.jar'
        run([archiver,'cf',str(plain),'-C',str(classes),'.'],work)
        stages.append({'name':'missing Main-Class: expected launch failure','result':run([launcher,*options,'-jar',str(plain)],work,failed_marker='no main manifest attribute')})
        plain_cp=os.pathsep.join(str(x) for x in [plain,library] if x)
        stages.append({'name':'no-entry JAR with explicit class','result':run([launcher,*options,'-cp',plain_cp,project['main']],work,expected=project['stdout'])})
        if library:
            absent=work/'compile-without-library';absent.mkdir()
            failure=invoke([compiler,*compile_options,'-classpath',str(absent),'-d',str(absent),*[str(source/p) for p in project['sources']]],work)
            assert failure['exit_code']!=0 and 'campus.lib' in failure['stderr'],failure
            assert not list(absent.rglob('*.class'))
            stages.append({'name':'missing compile dependency: expected compile failure','result':failure})
            stages.append({'name':'missing runtime dependency: expected execution failure','result':run([launcher,*options,'-cp',str(classes),project['main']],work,failed_marker='NoClassDefFoundError')})
            alone=work/'incomplete release';alone.mkdir();shutil.copy2(application,alone/'app.jar')
            stages.append({'name':'JAR released without lib: expected execution failure','result':run([launcher,*options,'-jar',str(alone/'app.jar')],elsewhere,failed_marker='NoClassDefFoundError')})
            no_resource=work/'no-resource.jar'
            run([archiver,'cfm',str(no_resource),str(mf),'-C',str(classes),project['main'].replace('.','/')+'.class'],work)
            stages.append({'name':'missing resource: expected execution failure','result':run([launcher,*options,'-jar',str(no_resource)],work,failed_marker='missing resource')})
        results.append({'id':project['id'],'main':project['main'],'passed':True,'jar_entries':entries,'checks':stages})
    # Exercise the real PowerShell entry point, not only equivalent compiler commands.
    for project in projects:
        for mode in ['Run','Jar']:
            result=run(['powershell.exe','-NoProfile','-ExecutionPolicy','Bypass','-File',str(ROOT/'project.ps1'),'-Project',project['id'],'-Mode',mode],ROOT,expected=project['stdout'])
            results.append({'id':project['id'],'entry_mode':mode,'passed':True,'result':result})
    sample=projects[0]
    bad=build/'intentional bad source';shutil.copytree(ROOT/sample['root'],bad)
    main_source=next(p for p in bad.rglob('*.java') if 'public static void main' in p.read_text(encoding='utf-8'))
    text=main_source.read_text(encoding='utf-8').replace('System.out.println(Greeting.forName(name));','System.out.println(Greeting.forName(name))')
    main_source.write_text(text,encoding='utf-8')
    failure=invoke(['powershell.exe','-NoProfile','-ExecutionPolicy','Bypass','-File',str(ROOT/'project.ps1'),'-Project',sample['id'],'-SourceRoot',str(bad),'-Mode','Jar'],ROOT)
    assert failure['exit_code']!=0 and sample['stdout'].strip() not in failure['stdout'],failure
    results.append({'id':sample['id'],'entry_mode':'expected compile failure; no stale JAR','passed':True,'result':failure})
    report={'passed':True,'scope':'Authored multi-file projects; not student assessment','projects':len(projects),
            'checks':sum(len(x.get('checks',[])) or 1 for x in results),'manifest_sha256':digest(manifest),
            'runner_sha256':digest(ROOT/'project.ps1'),'source_sha256':source_hashes,'lecture_sha256':lecture_hashes,'results':results}
    (ROOT/'outputs/项目结构验证.json').write_text(json.dumps(report,ensure_ascii=False,indent=2),encoding='utf-8')
    print(json.dumps({k:report[k] for k in ['passed','projects','checks']},ensure_ascii=False))
    return 0

if __name__=='__main__':
    sys.exit(main())
