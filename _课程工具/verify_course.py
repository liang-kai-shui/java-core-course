"""Validate the authored Java examples and exercises; never grade student work."""
from __future__ import annotations
import concurrent.futures
import hashlib
import json
import os
from pathlib import Path
import shutil
import subprocess
import sys
import time
import uuid

ROOT = Path(__file__).resolve().parents[1]

def find_jdk():
    config_path = ROOT / '.java-course.json'
    config = json.loads(config_path.read_text(encoding='utf-8-sig')) if config_path.exists() else {}
    candidates = [config.get('jdkHome'), os.environ.get('JAVA_HOME')]
    if shutil.which('javac'):
        candidates.append(str(Path(shutil.which('javac')).parent.parent))
    for base in [Path(os.environ.get('ProgramFiles', 'C:/Program Files')) / 'Java',
                 Path(os.environ.get('ProgramFiles', 'C:/Program Files')) / 'Eclipse Adoptium']:
        if base.is_dir():
            candidates.extend(str(p) for p in sorted(base.iterdir(), reverse=True) if p.is_dir())
    for candidate in filter(None, candidates):
        home = Path(candidate)
        suffix = '.exe' if os.name == 'nt' else ''
        if all((home / 'bin' / (name + suffix)).is_file() for name in ['javac', 'java']):
            return home, suffix
    raise RuntimeError('No JDK found. Configure .java-course.json or JAVA_HOME.')

def invoke(command, cwd, stdin=''):
    result = subprocess.run(command, cwd=cwd, input=stdin.encode('utf-8'),
                            stdout=subprocess.PIPE, stderr=subprocess.PIPE, timeout=20)
    return {'exit_code': result.returncode,
            'stdout': result.stdout.decode('utf-8', errors='replace').replace('\r\n', '\n'),
            'stderr': result.stderr.decode('utf-8', errors='replace').replace('\r\n', '\n')}

def check_one(index, item, jdk, suffix, build):
    source = ROOT / item['path']
    work = build / f'{index:03}-{source.stem}'
    work.mkdir(parents=True)
    classes = work / 'classes'
    classes.mkdir()
    compile_command = [str(jdk / 'bin' / ('javac' + suffix)), '--release', '8', '-encoding', 'UTF-8',
                       '-Xlint:all,-options', '-XDrawDiagnostics', '-d', str(classes), str(source)]
    compiled = invoke(compile_command, work)
    result = {'source': item['path'], 'sha256': hashlib.sha256(source.read_bytes()).hexdigest(),
              'kind': item['kind'], 'compile_command': compile_command, 'compile': compiled, 'runs': []}
    if item['kind'] == 'compile_error':
        expected = ('compiler.err.class.public.should.be.in.file' if source.stem == 'WrongFileName'
                    else 'compiler.err.expected')
        assert compiled['exit_code'] != 0 and expected in compiled['stderr'], result
        result['passed'] = True
        return result
    assert compiled['exit_code'] == 0, result
    # Normal instructional sources must not hide compiler warnings.
    assert not compiled['stderr'].strip(), result
    variants = [item] + item.get('cases', [])
    for variant in variants:
        args = [s.replace('{work}', str(work)) for s in variant.get('args', item.get('args', []))]
        command = [str(jdk / 'bin' / ('java' + suffix)), '-Dfile.encoding=UTF-8',
                   '-Dstdout.encoding=UTF-8', '-Dstderr.encoding=UTF-8', '-Duser.language=en',
                   '-cp', str(classes), item['class']] + args
        run = invoke(command, work, variant.get('stdin', item.get('stdin', '')))
        run['command'] = command
        run['stdin'] = variant.get('stdin', item.get('stdin', ''))
        result['runs'].append(run)
        if item['kind'] == 'launch_error':
            assert run['exit_code'] != 0 and 'Main method not found' in run['stderr'], result
        elif item['kind'] == 'runtime_error':
            assert run['exit_code'] != 0 and 'ArrayIndexOutOfBoundsException' in run['stderr'], result
        else:
            assert run['exit_code'] == 0, result
            expected = variant.get('stdout', item.get('stdout'))
            if expected is not None:
                assert run['stdout'] == expected, {'expected': expected, 'result': result}
            for marker in variant.get('contains', item.get('contains')) or []:
                assert marker in run['stdout'], result
            if source.stem == 'FileUtf8Demo' and args:
                assert Path(args[0]).read_text(encoding='utf-8') == '你好，Java！\n'
    result['passed'] = True
    return result

def main():
    start = time.monotonic()
    items = json.loads((ROOT / '_课程工具/checks.json').read_text(encoding='utf-8'))
    course = json.loads((ROOT / '_课程工具/course.json').read_text(encoding='utf-8'))
    authored = {p.relative_to(ROOT).as_posix() for lesson in course['lessons']
                for sub in ['code', '参考答案', 'error_lab']
                for p in (ROOT / lesson['folder'] / sub).glob('*.java')}
    assert authored == {item['path'] for item in items}, 'Manifest must cover every authored Java source.'
    for lesson in course['lessons']:
        assert lesson['book_start'] + 18 == lesson['pdf_start']
        assert lesson['book_end'] + 18 == lesson['pdf_end']
        for kind in ['讲义.md', '练习.md']:
            content = (ROOT / lesson['folder'] / kind).read_text(encoding='utf-8')
            assert f'书本 p{lesson["book_start"]}–{lesson["book_end"]}' in content
            assert f'PDF p{lesson["pdf_start"]}–{lesson["pdf_end"]}' in content
        answer = (ROOT / lesson['folder'] / '参考答案/答案.md').read_text(encoding='utf-8')
        assert all(q in answer for q in lesson['questions']), lesson['id']
    jdk, suffix = find_jdk()
    version = invoke([str(jdk / 'bin' / ('java' + suffix)), '-version'], ROOT)
    build = ROOT / '.build' / ('verify-' + uuid.uuid4().hex)
    build.mkdir(parents=True)
    results, failures = [], []
    with concurrent.futures.ThreadPoolExecutor(max_workers=3) as pool:
        future_to_item = {pool.submit(check_one, i, item, jdk, suffix, build): item
                          for i, item in enumerate(items)}
        for future in concurrent.futures.as_completed(future_to_item):
            item = future_to_item[future]
            try:
                results.append(future.result())
            except Exception as exc:
                failures.append({'source': item['path'], 'error': str(exc)})
    results.sort(key=lambda x: x['source'])
    report = {'scope': 'Course sources only; not student assessment', 'jdk': str(jdk),
              'checks_sha256': hashlib.sha256((ROOT / '_课程工具/checks.json').read_bytes()).hexdigest(),
              'course_sha256': hashlib.sha256((ROOT / '_课程工具/course.json').read_bytes()).hexdigest(),
              'runtime_version': version, 'release': '8', 'lessons': len(course['lessons']),
              'sources': len(items), 'cases': sum(1 + len(i.get('cases', [])) for i in items),
              'normal_sources': sum(i['kind'] == 'normal' for i in items),
              'expected_error_sources': sum(i['kind'] != 'normal' for i in items),
              'passed': not failures, 'elapsed_seconds': round(time.monotonic() - start, 2),
              'results': results, 'failures': failures}
    out = ROOT / 'outputs'
    out.mkdir(exist_ok=True)
    (out / '验证报告.json').write_text(json.dumps(report, ensure_ascii=False, indent=2), encoding='utf-8')
    print(json.dumps({key: report[key] for key in ['lessons', 'sources', 'cases', 'passed', 'elapsed_seconds', 'failures']}, ensure_ascii=False, indent=2))
    return 0 if report['passed'] else 1

if __name__ == '__main__':
    sys.exit(main())
