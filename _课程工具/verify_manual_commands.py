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
    for id in ['L02','S04','S05','S06']:
        unit=units[id]
        path=ROOT/unit['folder']/'讲义.md'
        text=path.read_text(encoding='utf-8')
        local=target/unit['folder']/'my_code'
        local.mkdir(parents=True)
        if id=='L02':
            for code in re.findall(r'^```java\n(.*?)^```',text,re.S|re.M):
                cls=re.search(r'public class (\w+)',code).group(1)
                (local/(cls+'.java')).write_text(code,encoding='utf-8')
        else:
            shutil.copytree(ROOT/unit['folder']/'code/project',local/'project')
        blocks=re.findall(r'^```powershell\n(.*?)^```',text,re.S|re.M)
        # Run each lesson from a fresh root, preserving variables and working directory between its blocks.
        script=target/(id+'-commands.ps1')
        initial="$ErrorActionPreference='Stop'\n[Console]::OutputEncoding=[Text.UTF8Encoding]::new($false)\n"
        content=initial
        for i,block in enumerate(blocks):
            content+=block+f"\nWrite-Output 'COURSE_BLOCK_{i}_DONE'\n"
        script.write_text(content,encoding='utf-8-sig')
        run=subprocess.run(['powershell.exe','-NoProfile','-ExecutionPolicy','Bypass','-File',str(script)],cwd=target,stdout=subprocess.PIPE,stderr=subprocess.PIPE,timeout=60)
        stdout=run.stdout.decode('utf-8',errors='replace').replace('\r\n','\n')
        stderr=run.stderr.decode('utf-8',errors='replace')
        assert run.returncode==0,{'lesson':id,'stdout':stdout,'stderr':stderr}
        assert all(f'COURSE_BLOCK_{i}_DONE' in stdout for i in range(len(blocks))),id
        expected={'L02':['version A','count=0','count=2'],'S04':['Hello, Lin','Hello, Mei'],'S05':['packed: Lin','META-INF/MANIFEST.MF'],'S06':['[讲义先学，教材深化。]']}[id]
        assert all(marker in stdout for marker in expected),{'lesson':id,'stdout':stdout}
        results.append({'id':id,'passed':True,'blocks':len(blocks),'lecture':path.relative_to(ROOT).as_posix(),
                        'sha256':hashlib.sha256(path.read_bytes()).hexdigest(),'stdout':stdout,'stderr':stderr})
    report={'passed':True,'scope':'Literal lecture commands in copied learning workspace','lessons':len(results),'blocks':sum(x['blocks'] for x in results),'results':results}
    (ROOT/'outputs/讲义命令验证.json').write_text(json.dumps(report,ensure_ascii=False,indent=2),encoding='utf-8')
    print(json.dumps({k:report[k] for k in ['passed','lessons','blocks']}))

if __name__=='__main__':main()
