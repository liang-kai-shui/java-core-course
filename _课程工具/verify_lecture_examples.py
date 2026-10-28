"""Compile and run complete lecture examples; exclude fragments and student work."""
from __future__ import annotations

import concurrent.futures
import hashlib
import json
from pathlib import Path
import re
import sys
import uuid

from verify_course import ROOT, find_jdk, invoke


def check_case(index, example, case, jdk, suffix, build):
    work = build / f'{index:03}-{example["class"]}'
    classes = work / 'classes'
    classes.mkdir(parents=True)
    code = example['code']
    for before, after in case.get('replace', []):
        assert before in code, (example['class'], before)
        code = code.replace(before, after)
    source = work / (example['class'] + '.java')
    source.write_text(code, encoding='utf-8')
    command = [str(jdk / 'bin' / ('javac' + suffix)), '-J-Dfile.encoding=UTF-8',
               '-J-Dstdout.encoding=UTF-8', '-J-Dstderr.encoding=UTF-8', '--release', '8',
               '-encoding', 'UTF-8', '-Xlint:all,-options', '-d', str(classes), str(source)]
    compiled = invoke(command, work)
    result = {'lecture': example['lecture'], 'class': example['class'],
              'case': case.get('name', 'base'), 'source_sha256': hashlib.sha256(code.encode()).hexdigest(),
              'compile_command': command, 'compile': compiled}
    if case.get('kind') == 'compile_error':
        assert compiled['exit_code'] != 0, result
        result['passed'] = True
        return result
    # No old output is run on a compiler failure.
    assert compiled['exit_code'] == 0, result
    if case.get('expected_warning'):
        assert case['expected_warning'] in compiled['stderr'], result
    else:
        assert not compiled['stderr'].strip(), result
    run_command = [str(jdk / 'bin' / ('java' + suffix)), '-Dfile.encoding=UTF-8',
                   '-Dstdout.encoding=UTF-8', '-Dstderr.encoding=UTF-8',
                   '-cp', str(classes), example['class'], *case.get('args', [])]
    run = invoke(run_command, work, case.get('stdin', ''))
    result.update(run_command=run_command, stdin=case.get('stdin', ''), run=run)
    if case.get('kind') == 'runtime_error':
        assert run['exit_code'] != 0 and case['exception'] in run['stderr'], result
    else:
        assert run['exit_code'] == 0, result
        if 'stdout' in case:
            assert run['stdout'] == case['stdout'], result
        else:
            assert re.fullmatch(case['stdout_regex'], run['stdout']), result
        for rel, expected in case.get('files', {}).items():
            assert (work / rel).read_bytes() == expected.encode('utf-8'), result
        for rel, expected in case.get('files_text', {}).items():
            assert (work / rel).read_text(encoding='utf-8') == expected, result
    result['passed'] = True
    return result


def main():
    if hasattr(sys.stdout, 'reconfigure'):
        sys.stdout.reconfigure(encoding='utf-8')
    manifest = ROOT / '_课程工具/lecture_checks.json'
    checks = json.loads(manifest.read_text(encoding='utf-8'))
    lessons = json.loads((ROOT / '_课程工具/course.json').read_text(encoding='utf-8'))['lessons']
    examples, lecture_hashes = [], {}
    for lesson in lessons:
        path = ROOT / lesson['folder'] / '讲义.md'
        text = path.read_text(encoding='utf-8')
        lecture_hashes[path.relative_to(ROOT).as_posix()] = hashlib.sha256(path.read_bytes()).hexdigest()
        blocks = re.findall(r'^```java\s*\n(.*?)^```', text, re.S | re.M)
        for code in blocks:
            match = re.search(r'public class (\w+)', code)
            assert match and 'public static void main(String[] args)' in code, path
            examples.append({'class': match.group(1), 'code': code,
                             'lecture': path.relative_to(ROOT).as_posix()})
    names = [e['class'] for e in examples]
    assert len(names) == len(set(names)) and set(names) == set(checks), 'Inline fixture coverage mismatch'
    jdk, suffix = find_jdk()
    build = ROOT / '.build' / ('lecture-verify-' + uuid.uuid4().hex)
    results, failures = [], []
    tasks = [(example, case) for example in examples for case in checks[example['class']]]
    with concurrent.futures.ThreadPoolExecutor(max_workers=3) as pool:
        pending = {pool.submit(check_case, i, example, case, jdk, suffix, build):
                   (example['class'], case.get('name', 'base'))
                   for i, (example, case) in enumerate(tasks)}
        for future in concurrent.futures.as_completed(pending):
            try:
                results.append(future.result())
            except Exception as exc:
                failures.append({'example': pending[future], 'error': str(exc)})
    report = {'passed': not failures, 'scope': 'Authored lecture programs; not student assessment',
              'jdk': str(jdk), 'release': '8', 'programs': len(examples), 'cases': len(tasks),
              'manifest_sha256': hashlib.sha256(manifest.read_bytes()).hexdigest(),
              'lecture_sha256': lecture_hashes, 'results': sorted(results, key=lambda r: (r['class'], r['case'])),
              'failures': failures}
    out = ROOT / 'outputs'
    out.mkdir(exist_ok=True)
    (out / '讲义程序验证.json').write_text(json.dumps(report, ensure_ascii=False, indent=2), encoding='utf-8')
    print(json.dumps({k: report[k] for k in ['passed', 'programs', 'cases', 'failures']}, ensure_ascii=False, indent=2))
    return 0 if report['passed'] else 1


if __name__ == '__main__':
    sys.exit(main())
