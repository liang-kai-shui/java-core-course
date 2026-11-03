"""Execute the literal PowerShell instructions from the command-focused lectures."""
from pathlib import Path
import hashlib
import json
import re
import shutil
import subprocess
import sys
import uuid

ROOT=Path(__file__).resolve().parents[1]

def main():
    if hasattr(sys.stdout,'reconfigure'):sys.stdout.reconfigure(encoding='utf-8')
    course=json.loads((ROOT/'_课程工具/course.json').read_text(encoding='utf-8'))
    units={x['id']:x for x in course['lessons']}
    target=ROOT/'.build'/('manual-instructions-'+uuid.uuid4().hex)
    target.mkdir(parents=True)
    for name in ['java.ps1','project.ps1']:
        shutil.copy2(ROOT/name,target/name)
    (target/'_课程工具').mkdir()
    for name in ['resolve-jdk.ps1','projects.json']:
        shutil.copy2(ROOT/'_课程工具'/name,target/'_课程工具'/name)
    (target/'.java-course.json').write_text('{"jdkHome":"","release":"8"}',encoding='utf-8')
    results=[]
    for id in ['L02','S04','S05','S06','L17','L32','L47','L48','L54']:
        unit=units[id]
        path=ROOT/unit['folder']/'讲义.md'
        text=path.read_text(encoding='utf-8')
        local=target/unit['folder']/'my_code'
        local.mkdir(parents=True)
        if id=='L02':
            for code in re.findall(r'^```java\n(.*?)^```',text,re.S|re.M):
                cls=re.search(r'public class (\w+)',code).group(1)
                (local/(cls+'.java')).write_text(code,encoding='utf-8')
        elif id in ['L17','L32','L47','L48','L54']:
            shutil.copytree(ROOT/unit['folder']/'code',target/unit['folder']/'code')
            if id=='L47':
                shutil.copytree(ROOT/unit['folder']/'code/project',local/'project')
        else:
            shutil.copytree(ROOT/unit['folder']/'code/project',local/'project')
        # L48's default program opens a window; the marked block tests packaging
        # without pretending to verify an interactive desktop session.
        pattern=(r'<!-- verify-command: package -->\n```powershell\n(.*?)^```'
                 if id=='L48' else r'^```powershell\n(.*?)^```')
        blocks=re.findall(pattern,text,re.S|re.M)
        assert blocks,id
        # Run each lesson from a fresh root, preserving variables and working directory between its blocks.
        script=target/(id+'-commands.ps1')
        initial="$ErrorActionPreference='Stop'\n[Console]::OutputEncoding=[Text.UTF8Encoding]::new($false)\n"
        content=initial
        for i,block in enumerate(blocks):
            content+=block+f"\nWrite-Output 'COURSE_BLOCK_{i}_DONE'\n"
        script.write_text(content,encoding='utf-8-sig')
        run=subprocess.run(['powershell.exe','-NoProfile','-ExecutionPolicy','Bypass','-File',str(script)],cwd=target,stdout=subprocess.PIPE,stderr=subprocess.PIPE,timeout=180 if id=='L48' else 60)
        stdout=run.stdout.decode('utf-8',errors='replace').replace('\r\n','\n')
        stderr=run.stderr.decode('utf-8',errors='replace')
        assert run.returncode==0,{'lesson':id,'stdout':stdout,'stderr':stderr}
        assert all(f'COURSE_BLOCK_{i}_DONE' in stdout for i in range(len(blocks))),id
        expected={'L02':['version A','count=0','count=2'],'S04':['Hello, Lin','Hello, Mei'],'S05':['packed: Lin','META-INF/MANIFEST.MF'],'S06':['[讲义先学，教材深化。]'],'L17':['12','True'],'L32':['FINE:step','enabled=false','enabled=true'],
                  'L47':['数组','[数组]'],'L48':['CourseWindow.exe'],'L54':['live-size=3','saved','completed-minutes=50','restored=3']}[id]
        assert all(marker in stdout for marker in expected),{'lesson':id,'stdout':stdout}
        if id=='L17':
            html=(target/'.build/L17-api/DocumentedCost.html').read_text(encoding='utf-8')
            assert 'pages per copy' in html and 'total sheets' in html
        if id=='L48':
            images=list((target/'.build').glob('package-*/image/CourseWindow/CourseWindow.exe'))
            assert len(images)==1 and images[0].is_file(), 'Application image not created'
        if id=='L54':
            data=local/'records.tsv'
            assert data.read_text(encoding='utf-8')=='1\t30\ttrue\t数组\n2\t20\ttrue\t循环\n3\t15\tfalse\t类\n'
        results.append({'id':id,'passed':True,'blocks':len(blocks),'lecture':path.relative_to(ROOT).as_posix(),
                        'sha256':hashlib.sha256(path.read_bytes()).hexdigest(),'stdout':stdout,'stderr':stderr})
    report={'passed':True,'scope':'Literal lecture commands in copied learning workspace','lessons':len(results),'blocks':sum(x['blocks'] for x in results),'results':results}
    (ROOT/'outputs/讲义命令验证.json').write_text(json.dumps(report,ensure_ascii=False,indent=2),encoding='utf-8')
    print(json.dumps({k:report[k] for k in ['passed','lessons','blocks']}))

if __name__=='__main__':main()
