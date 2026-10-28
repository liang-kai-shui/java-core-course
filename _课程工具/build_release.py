"""Build portable offline HTML and an allow-listed, textbook-free course archive."""
from __future__ import annotations
import hashlib
import html
from html.parser import HTMLParser
import json
from pathlib import Path
import posixpath
import re
import sys
from urllib.parse import quote, unquote, urlsplit
import zipfile
import markdown
from markdown.extensions.toc import slugify_unicode

ROOT = Path(__file__).resolve().parents[1]
DOCS = ROOT / 'docs'
OUT = ROOT / 'outputs'
COURSE = json.loads((ROOT / '_课程工具/course.json').read_text(encoding='utf-8'))
EXERCISES = json.loads((ROOT / '_课程工具/exercises.json').read_text(encoding='utf-8'))
ROOT_FILES = ['README.md', '开始学习.html', 'AGENTS.md', '学习路线图.md', '教材目录.md', '页码对应说明.md',
              '环境说明.md', '给其他LLM的批改指南.md', '学习进度模板.md', '维护与打包.md',
              'java.ps1', 'check-java.ps1', '.gitignore', '.gitattributes', 'LICENSE', 'LICENSE-DOCS', '.java-course.example.json', '.vscode/tasks.json', '.vscode/extensions.json']

CSS = '''
:root{color-scheme:dark;--bg:#111c21;--panel:#16252c;--text:#e5ede9;--muted:#99afa9;--line:#2b4148;--accent:#b9d78b;--gold:#dfbd7c;--code:#0c171c}
:root[data-theme=light]{color-scheme:light;--bg:#f5f4ed;--panel:#fffef8;--text:#253d42;--muted:#57716e;--line:#d4dfd5;--accent:#416d35;--gold:#8a5f18;--code:#edf0e7}
*{box-sizing:border-box}body{margin:0;background:var(--bg);color:var(--text);font:16px/1.85 system-ui,'Microsoft YaHei',sans-serif}a{color:var(--accent);text-underline-offset:4px;overflow-wrap:anywhere}a:hover{color:var(--gold)}button{font:inherit;cursor:pointer;border:1px solid var(--line);background:var(--panel);color:var(--text);border-radius:8px;padding:5px 13px}button:focus-visible,a:focus-visible,input:focus-visible{outline:2px solid var(--gold);outline-offset:4px}
.top{height:78px;display:flex;align-items:center;justify-content:space-between;gap:12px;padding:0 30px;border-bottom:1px solid var(--line);background:var(--panel)}.brand{font-size:19px;font-weight:650;text-decoration:none;color:var(--text)}.brand span{color:var(--gold);margin-right:12px;letter-spacing:2px}.tag{font-size:12px;color:var(--muted);letter-spacing:1px}.tools{display:flex;align-items:center;gap:15px}.layout{display:grid;grid-template-columns:276px minmax(0,1fr)}aside{padding:25px 20px;position:sticky;top:0;height:100vh;overflow:auto;border-right:1px solid var(--line)}.navlabel{color:var(--muted);font-size:12px;letter-spacing:2px;margin:22px 0 10px}.navlink{display:block;padding:8px 12px;text-decoration:none;font-size:14px;border-radius:7px;margin:3px 0;color:var(--muted)}.navlink[aria-current=page]{color:var(--accent);background:var(--panel);box-shadow:inset 2px 0 var(--accent)}.navlink:hover{background:var(--panel)}.navsearch{background:var(--panel);border:1px solid var(--line);border-radius:8px;color:var(--text);padding:9px 10px;width:100%;font:inherit;font-size:13px}.side-note{font-size:12px;color:var(--muted);padding:14px 12px;border-top:1px solid var(--line);margin-top:26px}
main{min-width:0;padding:40px 5vw 70px}.crumb{font-size:12px;letter-spacing:1px;color:var(--muted);margin-bottom:20px}.edition{display:inline-block;border:1px solid var(--line);border-radius:20px;color:var(--gold);font-size:12px;padding:3px 12px}article{max-width:900px;margin:auto}h1{font-size:clamp(27px,3vw,38px);line-height:1.4;font-weight:650;letter-spacing:-.5px;margin:18px 0 25px}h2{font-size:23px;line-height:1.5;margin:42px 0 18px;padding-top:13px;border-top:1px solid var(--line)}h3{font-size:18px;line-height:1.6;margin-top:30px}p{margin:15px 0}li{margin:7px 0}strong{font-weight:650}blockquote{margin:22px 0;padding:12px 22px;border-left:3px solid var(--gold);background:var(--panel);border-radius:0 8px 8px 0}blockquote p{margin:6px 0}.table-wrap{max-width:100%;overflow:auto;margin:22px 0;border:1px solid var(--line);border-radius:8px}table{border-collapse:collapse;width:100%;font-size:14px;line-height:1.75}th,td{padding:11px 14px;text-align:left;border-bottom:1px solid var(--line);min-width:72px;vertical-align:top}th{background:var(--panel);color:var(--gold)}tr:last-child td{border-bottom:0}pre{background:var(--code);border:1px solid var(--line);border-radius:9px;padding:20px;overflow:auto;line-height:1.7;font-size:13px;tab-size:4}code{font-family:Consolas,'Cascadia Mono',monospace}p code,li code,td code{font-size:.9em;background:var(--panel);border-radius:4px;padding:2px 5px;overflow-wrap:anywhere}details{margin:16px 0;border:1px solid var(--line);border-radius:8px;padding:12px 17px}summary{cursor:pointer;color:var(--accent)}.toc{font-size:14px;background:var(--panel);padding:17px 22px;border-radius:9px;margin:25px 0}.toc ul{padding-left:20px;margin:0}.toc li{margin:3px 0}.pager{display:flex;justify-content:space-between;gap:20px;border-top:1px solid var(--line);margin-top:45px;padding-top:22px;font-size:14px}.footer{font-size:12px;color:var(--muted);margin-top:40px}.bundle-section{padding-top:30px;margin-top:40px;border-top:4px solid var(--line)}
@media(max-width:800px){.top{height:auto;min-height:70px;padding:14px 18px}.tag,.all-link{display:none}.layout{display:block}aside{position:relative;height:auto;border-right:0;border-bottom:1px solid var(--line);padding:15px 18px}aside nav{max-height:270px;overflow:auto}.navlabel{margin-top:10px}.side-note{display:none}main{padding:25px 20px 50px}h1{font-size:28px}h2{font-size:21px}pre{padding:14px}td,th{padding:9px 11px}}
@media print{aside,.tools,.top,.pager,.crumb{display:none}.layout{display:block}main{padding:0}body{background:white;color:black;font-size:11pt}article{max-width:none}pre,blockquote{background:#fafafa;color:black;border-color:#ccc}a{color:black}details{break-inside:avoid}}
'''

CSS += '''
.top{position:sticky;top:0;z-index:20}.layout aside{top:78px;height:calc(100vh - 78px)}
.navlink{transition:color .16s ease,background-color .16s ease,box-shadow .16s ease}.navlink[hidden]{display:none!important}
.navlink[aria-current]{color:var(--accent);background:var(--panel);box-shadow:inset 2px 0 var(--accent);font-weight:600}
h1,h2,h3,h4,.bundle-section{scroll-margin-top:105px}.navlabel{border-top:1px solid var(--line);padding-top:16px}.navlabel:first-of-type{border-top:0}
.lesson-tabs{display:flex;flex-wrap:wrap;gap:8px;margin:20px 0 25px}.lesson-tabs a{padding:6px 15px;border:1px solid var(--line);border-radius:7px;text-decoration:none;font-size:14px}
.lesson-tabs a[aria-current]{background:var(--panel);color:var(--gold);border-color:var(--gold)}
.course-cards{display:grid;grid-template-columns:repeat(2,minmax(0,1fr));gap:16px;margin:25px 0}.course-card{padding:20px 22px;background:var(--panel);border:1px solid var(--line);border-radius:10px;min-width:0}
.course-card h3{margin:0 0 10px;font-size:19px}.course-card p{color:var(--muted);font-size:14px;margin:8px 0}.course-card .card-links{display:flex;flex-wrap:wrap;gap:15px;font-size:14px;margin-top:14px}.start-links{display:flex;flex-wrap:wrap;gap:12px;margin:24px 0}.start-links a{display:inline-block;padding:9px 17px;border:1px solid var(--line);border-radius:8px;background:var(--panel);text-decoration:none}
.pager a{max-width:48%}.mode-note{font-size:14px;color:var(--muted);padding:12px 17px;border:1px solid var(--line);border-radius:8px;margin:24px 0}
@media(max-width:800px){.layout aside{position:relative;top:0;height:auto}.course-cards{grid-template-columns:1fr}.top .tag{display:none}.top{padding:12px 16px}.brand{font-size:16px}.brand span{margin-right:7px}.tools{gap:9px;font-size:13px}.tools button{font-size:13px;padding:5px 9px}.lesson-tabs a{padding:6px 11px}}
@media(prefers-reduced-motion:reduce){.navlink{transition:none}}
'''
JS = (ROOT / '_课程工具/course_navigation.js').read_text(encoding='utf-8')
ASSET_VERSION = hashlib.sha256((CSS + JS).encode('utf-8')).hexdigest()[:12]

def collect_files():
    files = {Path(p) for p in ROOT_FILES}
    files.update(p.relative_to(ROOT) for p in (ROOT / '附录').glob('*.md'))
    files.update(p.relative_to(ROOT) for p in (ROOT / '_课程工具').glob('*') if p.suffix in ['.py', '.json', '.txt', '.ps1', '.js', '.cjs'])
    for lesson in COURSE['lessons']:
        folder = ROOT / lesson['folder']
        for rel in ['讲义.md', '练习.md', 'my_code/README.md']:
            files.add((folder / rel).relative_to(ROOT))
        for sub in ['code', '参考答案', 'error_lab']:
            files.update(p.relative_to(ROOT) for p in (folder / sub).glob('*') if p.suffix in ['.md', '.java'])
    return sorted(files, key=lambda p: p.as_posix())

def doc_name(rel):
    if rel == Path('README.md'):
        return 'course-info.html'
    if rel.name == '讲义.md':
        return rel.parts[0].split('_')[0].lower() + '-lesson.html'
    return 'page-' + hashlib.sha256(rel.as_posix().encode('utf-8')).hexdigest()[:12] + '.html'

def doc_id(rel):
    return 'doc-' + hashlib.sha256(rel.as_posix().encode('utf-8')).hexdigest()[:12]

def public_text(rel):
    # A student's exercise file is editable. Never infer blank questions by deleting answers.
    if rel.name == '练习.md':
        text = EXERCISES[rel.as_posix()]
    else:
        text = (ROOT / rel).read_text(encoding='utf-8-sig')
    # Redirect only the public link. The instructions still tell learners to maintain their own record.
    return text.replace('](进度追踪.md)', '](学习进度模板.md)').replace('](../进度追踪.md)', '](../学习进度模板.md)')

def export_public_snapshot(files):
    """Refresh allow-listed publication files, preserving .git and learner-owned files."""
    publish = ROOT / '可分发版'
    publish.mkdir(exist_ok=True)
    names = []
    for rel in files:
        destination = publish / rel
        destination.parent.mkdir(parents=True, exist_ok=True)
        if rel.suffix == '.md':
            destination.write_text(public_text(rel), encoding='utf-8', newline='\n')
        else:
            destination.write_bytes((ROOT / rel).read_bytes())
        names.append(rel.as_posix())
    for source in DOCS.rglob('*'):
        if source.is_file():
            rel = source.relative_to(ROOT)
            destination = publish / rel
            destination.parent.mkdir(parents=True, exist_ok=True)
            destination.write_bytes(source.read_bytes())
            names.append(rel.as_posix())
    (publish / '.java-course.json').write_text('{"jdkHome":"","release":"8"}\n', encoding='utf-8', newline='\n')
    names.append('.java-course.json')
    # A previous export manifest allows stale generated files to be identified; never touch .git or personal submissions.
    manifest_path = publish / '.publication-files.json'
    previous = json.loads(manifest_path.read_text(encoding='utf-8')) if manifest_path.exists() else []
    for name in set(previous) - set(names):
        stale = (publish / name).resolve()
        assert stale.is_relative_to(publish.resolve()) and '.git' not in stale.parts
        if stale.is_file():
            stale.unlink()
    manifest_path.write_text(json.dumps(sorted(names), ensure_ascii=False, indent=2) + '\n', encoding='utf-8')
    actual = {p.relative_to(publish).as_posix() for p in publish.rglob('*')
              if p.is_file() and '.git' not in p.relative_to(publish).parts}
    unexpected = actual - set(names) - {'.publication-files.json'}
    assert not unexpected, ('Publication folder has extra files; preserve them outside it before sharing: '
                            + ', '.join(sorted(unexpected)))
    return publish

class Rewrite(HTMLParser):
    def __init__(self, source, names, combined=False):
        super().__init__(convert_charrefs=False)
        self.source, self.names, self.combined = source, names, combined
        self.parts = []

    def handle_starttag(self, tag, attrs):
        rewritten = []
        for key, value in attrs:
            if key == 'id' and self.combined:
                value = doc_id(self.source) + '-' + value
            if key == 'href':
                parsed = urlsplit(value)
                if not parsed.scheme and not parsed.netloc:
                    if parsed.path:
                        target = Path(posixpath.normpath((self.source.parent / unquote(parsed.path)).as_posix()))
                    else:
                        target = self.source
                    if target.name == '进度追踪.md':
                        target = target.with_name('学习进度模板.md')
                    if target in self.names:
                        if self.combined:
                            value = '#' + doc_id(target) + ('-' + parsed.fragment if parsed.fragment else '')
                        else:
                            value = self.names[target] + ('#' + parsed.fragment if parsed.fragment else '')
                    elif target.name in ['LICENSE', 'LICENSE-DOCS']:
                        value = 'https://github.com/liang-kai-shui/java-core-course/blob/main/' + target.name
                    elif parsed.path:
                        value = '../' + quote(target.as_posix(), safe='/') + ('#' + parsed.fragment if parsed.fragment else '')
                    elif self.combined and parsed.fragment:
                        value = '#' + doc_id(self.source) + '-' + parsed.fragment
            rewritten.append((key, value))
        attr_text = ''.join(' ' + k + ('' if v is None else '="' + html.escape(v, quote=True) + '"') for k, v in rewritten)
        self.parts.append('<' + tag + attr_text + '>')

    def handle_endtag(self, tag): self.parts.append('</' + tag + '>')
    def handle_data(self, data): self.parts.append(data)
    def handle_entityref(self, name): self.parts.append('&' + name + ';')
    def handle_charref(self, name): self.parts.append('&#' + name + ';')

def render(rel, names, combined=False):
    md = markdown.Markdown(extensions=['fenced_code', 'tables', 'toc', 'sane_lists'],
                           extension_configs={'toc': {'slugify': slugify_unicode, 'toc_depth': '2-3'}})
    text = public_text(rel)
    body = md.convert(text)
    if rel.name == 'README.md' and rel.parent.name == 'code' or rel.name == '答案.md':
        sources = sorted((ROOT / rel.parent).glob('*.java'))
        if sources:
            body += '<h2>本目录源码 · 点击展开</h2>'
            for source in sources:
                body += '<details><summary>' + html.escape(source.name) + '</summary><pre><code>'
                body += html.escape(source.read_text(encoding='utf-8')) + '</code></pre></details>'
    parser = Rewrite(rel, names, combined)
    parser.feed(body)
    toc_parser = Rewrite(rel, names, combined)
    toc_parser.feed(md.toc if len(md.toc_tokens) > 1 else '')
    return ''.join(toc_parser.parts), ''.join(parser.parts)

def nav_owner(rel):
    if rel in ['home', 'combined']:
        return rel
    rel = Path(rel)
    if rel.name == 'README.md' and rel.parent.name in ['my_code', 'error_lab']:
        return rel.parent.parent / ('练习.md' if rel.parent.name == 'my_code' else 'code/README.md')
    return rel

def nav_key(rel):
    owner = nav_owner(rel)
    return owner if isinstance(owner, str) else doc_id(owner)

def navigation(names, active, combined=False):
    def link(rel, label, kind='guide', lesson=''):
        rel = rel if rel in ['home', 'combined'] else Path(rel)
        href = {'home': 'index.html', 'combined': '全一册.html'}.get(rel, names.get(rel))
        current = ' aria-current="page"' if nav_key(rel) == nav_key(active) else ''
        return f'<a class="navlink" href="{href}" data-doc="{nav_key(rel)}" data-page="{href}" data-kind="{kind}" data-lesson="{lesson}"{current}>{html.escape(label)}</a>'
    text = '<input id="navsearch" class="navsearch" aria-label="筛选课程导航" placeholder="筛选课号、讲义、练习…"><nav aria-label="课程导航">'
    text += '<div class="navlabel">课程入口</div>'
    text += link('home', '课程首页', 'home') + link('README.md', '课程说明')
    text += '<div class="navlabel">附录 · 先把基础讲清楚</div>'
    for rel, label in [('附录/零基础起飞.md', '零基础起飞'), ('附录/术语表.md', '术语表'),
                       ('环境说明.md', '运行环境'), ('页码对应说明.md', '书本与PDF页码')]:
        text += link(rel, label)
    for group, relative, kind in [('讲义', '讲义.md', 'lesson'), ('练习', '练习.md', 'exercise'),
                                   ('参考答案 · 先独立作答', '参考答案/答案.md', 'answers'),
                                   ('示例代码与运行命令', 'code/README.md', 'code')]:
        text += '<div class="navlabel">' + group + '</div>'
        for lesson in COURSE['lessons']:
            text += link(Path(lesson['folder']) / relative, lesson['id'] + ' · ' + lesson['title'], kind, lesson['id'])
    text += '<div class="navlabel">查阅与接续</div>'
    for rel, label in [('学习路线图.md', '全书路线 · 35课'), ('教材目录.md', '447条教材索引'),
                       ('给其他LLM的批改指南.md', 'AI批改与进度追踪'), ('学习进度模板.md', '个人进度模板'),
                       ('维护与打包.md', '维护与打包'), ('AGENTS.md', '课程写作约定')]:
        text += link(rel, label)
    text += '<div class="navlabel">连续阅读 · 可选</div>' + link('combined', '全一册（连续阅读）', 'combined')
    text += '</nav><p class="side-note">按课阅读，逐项练习。<br>个人作答直接写入练习.md。<br>教材PDF需自备。</p>'
    return text

def lesson_for(rel):
    rel = Path(rel)
    return next((lesson for lesson in COURSE['lessons'] if rel.parts[0] == lesson['folder']), None)

def lesson_tabs(names, rel):
    lesson = lesson_for(rel)
    if not lesson:
        return ''
    tabs = '<nav class="lesson-tabs" aria-label="本课内容切换">'
    for label, relative in [('讲义', '讲义.md'), ('示例', 'code/README.md'), ('练习', '练习.md'), ('参考答案', '参考答案/答案.md')]:
        target = Path(lesson['folder']) / relative
        selected = ' aria-current="page"' if nav_owner(rel) == target else ''
        tabs += f'<a href="{names[target]}"{selected}>{label}</a>'
    return tabs + '</nav>'

def page_pager(names, rel):
    route = [Path('README.md'), Path('附录/零基础起飞.md'), Path('环境说明.md'), Path('页码对应说明.md')]
    for lesson in COURSE['lessons']:
        route += [Path(lesson['folder']) / part for part in ['讲义.md', 'code/README.md', '练习.md']]
    owner = nav_owner(rel)
    lesson = lesson_for(rel)
    if lesson and Path(rel).parent.name == '参考答案':
        owner = Path(lesson['folder']) / '练习.md'
    if owner not in route:
        return f'<div class="pager"><a href="index.html">← 课程首页</a><a href="{names[Path(COURSE["lessons"][0]["folder"]) / "讲义.md"]}">开始L01 →</a></div>'
    position = route.index(owner)
    def label(target):
        unit = lesson_for(target)
        if unit:
            category = '示例' if target.parent.name == 'code' else target.stem
            return unit['id'] + ' ' + category
        return '课程说明' if target == Path('README.md') else target.stem
    previous = f'<a rel="prev" href="{names[route[position - 1]]}">← 上一页：{label(route[position - 1])}</a>' if position else '<a rel="prev" href="index.html">← 课程首页</a>'
    following = f'<a rel="next" href="{names[route[position + 1]]}">下一页：{label(route[position + 1])} →</a>' if position + 1 < len(route) else f'<a rel="next" href="{names[Path("学习路线图.md")]}">查看后续学习路线 →</a>'
    return '<div class="pager">' + previous + following + '</div>'

def home_content(names):
    content = '<h1>Java 入门课程</h1><p>跟着讲义理解、动手并完成基础练习，再读《Java核心技术 卷Ⅰ：基础知识》第10版深化。课程从电脑与程序的基础讲起，每课的讲义、示例、练习和参考答案分别阅读。</p>'
    content += f'<div class="start-links"><a href="{names[Path("附录/零基础起飞.md")]}">第一次学：零基础起飞 →</a><a href="{names[Path("README.md")]}">课程说明</a><a href="{names[Path("学习路线图.md")]}">学习路线</a></div>'
    content += '<h2>按课开始学习</h2><p>先读讲义，再运行示例、独立练习，最后复核答案。左侧也可直接切换到指定课次的讲义或练习。</p><div class="course-cards">'
    for lesson in COURSE['lessons']:
        folder = Path(lesson['folder'])
        content += f'<section class="course-card"><h3><a href="{names[folder / "讲义.md"]}">{lesson["id"]} · {html.escape(lesson["title"])}</a></h3><p>{html.escape(lesson["section"])}</p><p>书本 p{lesson["book_start"]}–{lesson["book_end"]} / PDF p{lesson["pdf_start"]}–{lesson["pdf_end"]}</p><div class="card-links">'
        for label, part in [('讲义', '讲义.md'), ('示例', 'code/README.md'), ('练习', '练习.md'), ('参考答案', '参考答案/答案.md')]:
            content += f'<a href="{names[folder / part]}">{label}</a>'
        content += '</div></section>'
    content += '</div><h2>学习与记录</h2><p>第1–3章共10课材料已备。L11–L35为后续规划，材料状态与学习是否过关分别记录。文字答案直接写在本地练习.md，代码写在my_code。</p>'
    content += '<details><summary>需要把所有内容连续阅读？</summary><p>可以使用<a href="全一册.html">全一册（连续阅读）</a>。日常学习使用上方分课入口，点击左侧导航也会打开相应的独立页面。</p></details>'
    return content

def shell(title, nav, content, pager='', inline=False, active='home'):
    styles = '<style>' + CSS + '</style>' if inline else f'<link rel="stylesheet" href="assets/course.css?v={ASSET_VERSION}">'
    script = '<script>' + JS + '</script>' if inline else f'<script src="assets/course.js?v={ASSET_VERSION}"></script>'
    mode_label = '切回分课阅读' if inline else '分课阅读'
    mode = 'combined' if inline else 'pages'
    edition = '连续阅读 · 全一册 · 左侧可切回独立页面' if inline else '分课阅读 · 第1–3章 · 10课已备 · 双页码'
    return '<!doctype html><html lang="zh-CN" data-theme="dark"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>' + html.escape(title) + ' · Java陪读</title>' + styles + f'</head><body id="top" data-reading-mode="{mode}" data-nav-doc="{nav_key(active)}">' + \
        '<header class="top"><a class="brand" href="index.html"><span>JAVA</span>一起把基础写扎实</a><div class="tools"><span class="tag">CORE JAVA · VOL. I · 10TH</span><a id="reading-mode" href="index.html">' + mode_label + '</a><button id="theme" aria-label="切换浅色与深色">明 / 暗</button></div></header>' + \
        '<div class="layout"><aside>' + nav + '</aside><main><article><div class="crumb">Java入门课程 / 按课学习</div><span class="edition">' + edition + '</span>' + content + pager + '<p class="footer">课程原创讲义与习题 · 讲义学习 → 动手练习 → 订正 · 教材深化</p></article></main></div>' + script + '</body></html>'

class Links(HTMLParser):
    def __init__(self):
        super().__init__()
        self.links, self.ids = [], []
    def handle_starttag(self, tag, attrs):
        for key, value in attrs:
            if key in ['href', 'src']: self.links.append(value)
            if key == 'id': self.ids.append(value)

def verify_links():
    pages = {}
    for p in [ROOT / '开始学习.html', *DOCS.glob('*.html')]:
        parser = Links(); parser.feed(p.read_text(encoding='utf-8')); pages[p.resolve()] = parser
        assert len(parser.ids) == len(set(parser.ids)), f'Duplicate anchors: {p}'
    count = 0
    for source, parser in pages.items():
        for link in parser.links:
            url = urlsplit(link)
            if url.scheme or url.netloc: continue
            target = (source.parent / unquote(url.path)).resolve() if url.path else source
            assert target.is_relative_to(ROOT), f'Link escapes project: {link}'
            assert target.exists(), f'Broken link in {source.name}: {link}'
            if url.fragment and target in pages:
                assert unquote(url.fragment) in pages[target].ids, f'Broken anchor: {link}'
            count += 1
    return {'html_pages': len(pages), 'internal_links': count,
            'anchors': sum(len(p.ids) for p in pages.values())}

def main():
    report_path = OUT / '验证报告.json'
    report = json.loads(report_path.read_text(encoding='utf-8'))
    assert report['passed'], 'Verify the course before building.'
    for name in ['checks', 'course']:
        assert report[name + '_sha256'] == hashlib.sha256((ROOT / f'_课程工具/{name}.json').read_bytes()).hexdigest(), 'Verification manifest changed.'
    for result in report['results']:
        assert hashlib.sha256((ROOT / result['source']).read_bytes()).hexdigest() == result['sha256'], 'Source changed after verification.'
    lecture_report = json.loads((OUT / '讲义程序验证.json').read_text(encoding='utf-8'))
    assert lecture_report['passed'], 'Verify complete lecture examples before building.'
    assert lecture_report['manifest_sha256'] == hashlib.sha256((ROOT / '_课程工具/lecture_checks.json').read_bytes()).hexdigest()
    for rel, digest in lecture_report['lecture_sha256'].items():
        assert hashlib.sha256((ROOT / rel).read_bytes()).hexdigest() == digest, 'Lecture changed after verification: ' + rel
    files = collect_files()
    expected_exercises = {(Path(lesson['folder']) / '练习.md').as_posix() for lesson in COURSE['lessons']}
    assert set(EXERCISES) == expected_exercises, 'Publication templates must cover exactly the authored lessons.'
    for lesson in COURSE['lessons']:
        template = EXERCISES[(Path(lesson['folder']) / '练习.md').as_posix()]
        assert all('## ' + question in template for question in lesson['questions'])
        assert '直接在这个' in template and '作答副本' not in template
    names = {rel: doc_name(rel) for rel in files if rel.suffix == '.md'}
    (DOCS / 'assets').mkdir(parents=True, exist_ok=True)
    (DOCS / 'assets/course.css').write_text(CSS, encoding='utf-8')
    (DOCS / 'assets/course.js').write_text(JS, encoding='utf-8')
    # Only remove stale generated HTML, never recurse or touch authored material.
    expected = set(names.values()) | {'index.html', '全一册.html'}
    for old in DOCS.glob('*.html'):
        if old.name not in expected:
            old.unlink()
    ordered = [Path('README.md'), Path('附录/零基础起飞.md'), Path('环境说明.md'), Path('页码对应说明.md'), Path('学习路线图.md')]
    for lesson in COURSE['lessons']:
        folder = Path(lesson['folder'])
        ordered += [folder / '讲义.md', folder / 'code/README.md', folder / '练习.md']
    ordered += [Path('给其他LLM的批改指南.md'), Path('学习进度模板.md'), Path('附录/术语表.md'), Path('教材目录.md')]
    ordered += sorted(set(names) - set(ordered), key=lambda p: p.as_posix())
    combined = []
    (DOCS / 'index.html').write_text(shell('Java入门课程', navigation(names, 'home'), home_content(names), active='home'), encoding='utf-8')
    for rel in ordered:
        toc, body = render(rel, names)
        text = public_text(rel)
        title = next(line.lstrip('# ').strip() for line in text.splitlines() if line.startswith('# '))
        pager = page_pager(names, rel)
        if toc:
            body = re.sub(r'(</h1>)', lambda m: m.group(1) + '<details class="page-toc"><summary>本页目录</summary>' + toc + '</details>', body, count=1)
        (DOCS / names[rel]).write_text(shell(title, navigation(names, rel), lesson_tabs(names, rel) + body, pager, active=rel), encoding='utf-8')
        _, content = render(rel, names, combined=True)
        combined.append(f'<section class="bundle-section" id="{doc_id(rel)}" data-nav-doc="{nav_key(rel)}" data-page="{names[rel]}">{content}</section>')
    mode_note = '<p class="mode-note">当前为全一册连续阅读。点击左侧任一条目可打开独立页面；也可用右上角“切回分课阅读”回到当前内容对应的页面。</p>'
    (DOCS / '全一册.html').write_text(shell('全一册', navigation(names, 'combined', True), mode_note + ''.join(combined), inline=True, active='combined'), encoding='utf-8')
    (DOCS / '.nojekyll').write_text('', encoding='utf-8')
    validation = verify_links()
    OUT.mkdir(exist_ok=True)
    archive = OUT / 'Java入门课程-第1-3章.zip'
    base = 'Java入门课程-第1-3章/'
    with zipfile.ZipFile(archive, 'w', zipfile.ZIP_DEFLATED) as z:
        for rel in files:
            if rel.suffix == '.md':
                z.writestr(base + rel.as_posix(), public_text(rel).encode('utf-8'))
            else:
                z.write(ROOT / rel, base + rel.as_posix())
        z.writestr(base + '.java-course.json', '{"jdkHome":"","release":"8"}\n')
        for p in sorted(DOCS.rglob('*')):
            if p.is_file(): z.write(p, base + p.relative_to(ROOT).as_posix())
    with zipfile.ZipFile(archive) as z:
        assert z.testzip() is None, 'ZIP integrity failed.'
        for name in z.namelist():
            parts = Path(name).parts
            assert not name.lower().endswith(('.pdf', '.png', '.class')), name
            assert '进度追踪.md' not in parts and '作答.md' not in parts, name
            assert not any(p in parts for p in ['tmp', '.build', 'outputs', '批改记录']), name
            if 'my_code' in parts: assert parts[-1] == 'README.md', name
        assert json.loads(z.read(base + '.java-course.json'))['jdkHome'] == ''
        for rel, template in EXERCISES.items():
            assert z.read(base + rel).decode('utf-8') == template, 'Exercise archive must contain only the blank canonical template.'
        validation['archive_files'] = len(z.namelist())
        archive_names = {name.removeprefix(base) for name in z.namelist()}
    publish = export_public_snapshot(files)
    published_names = set(json.loads((publish / '.publication-files.json').read_text(encoding='utf-8')))
    assert archive_names == published_names, 'ZIP and publication folder must contain the same course files.'
    with zipfile.ZipFile(archive) as z:
        assert all(z.read(base + name) == (publish / name).read_bytes() for name in published_names), 'ZIP/publication content differs.'
    digest = hashlib.sha256(archive.read_bytes()).hexdigest()
    archive.with_suffix('.zip.sha256').write_text(digest + '  ' + archive.name + '\n', encoding='utf-8')
    validation.update({'archive': archive.name, 'sha256': digest, 'bytes': archive.stat().st_size,
                       'course_source_checks': {'sources': report['sources'], 'cases': report['cases'], 'passed': report['passed']},
                       'textbook_included': False, 'personal_progress_included': False})
    validation.update({'publication_directory': publish.name, 'blank_exercises_verified': len(EXERCISES)})
    validation.update({'publication_files': len(published_names), 'publication_matches_archive': True,
                       'publication_extra_files': [], 'start_entry': '开始学习.html'})
    (OUT / '打包校验.json').write_text(json.dumps(validation, ensure_ascii=False, indent=2), encoding='utf-8')
    print(json.dumps(validation, ensure_ascii=False, indent=2))

if __name__ == '__main__':
    main()
