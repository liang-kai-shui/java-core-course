/* Browser regression check for the multi-page reader and the optional continuous reader. */
const {chromium} = require('playwright');
const fs = require('fs');
const path = require('path');
const crypto = require('crypto');
const {pathToFileURL} = require('url');
const root = path.resolve(__dirname, '..');
const course = JSON.parse(fs.readFileSync(path.join(__dirname, 'course.json'), 'utf8'));
const hash = source => crypto.createHash('sha256').update(source).digest('hex').slice(0, 12);
const doc = source => 'doc-' + hash(source);
const file = source => 'page-' + hash(source) + '.html';
const exercise = index => file(course.lessons[index - 1].folder + '/练习.md');
const answers = index => file(course.lessons[index - 1].folder + '/参考答案/答案.md');
const code = index => file(course.lessons[index - 1].folder + '/code/README.md');
const base = process.env.COURSE_BASE_URL || pathToFileURL(path.join(root, 'docs', 'index.html')).href.replace(/index\.html$/, '');
const output = path.join(root, 'outputs');
fs.mkdirSync(output, {recursive: true});

(async () => {
  const browser = await chromium.launch({headless: true, ...(process.env.COURSE_BROWSER ? {executablePath: process.env.COURSE_BROWSER} : {})});
  try {
    const context = await browser.newContext({viewport: {width: 1440, height: 1000}});
    const page = await context.newPage();
    const pageErrors = [];
    page.on('pageerror', error => pageErrors.push(error.message));
    const results = [];
    const nav = target => page.locator(`aside .navlink[data-page="${target}"]`);
    async function selected(target, locationOnly = false) {
      await page.waitForFunction(({target, locationOnly}) => {
        const active = [...document.querySelectorAll('aside .navlink[aria-current]')];
        return active.length === 1 && active[0].dataset.page === target
          && active[0].getAttribute('aria-current') === (locationOnly ? 'location' : 'page');
      }, {target, locationOnly}, {timeout: 5000});
    }
    async function independent(target, title) {
      await page.waitForURL(url => decodeURIComponent(url.pathname).endsWith('/' + target));
      await selected(target);
      if (await page.locator('.bundle-section').count()) throw Error('Normal reading must not concatenate course documents.');
      if (await page.locator('article h1').count() !== 1) throw Error('Independent page should have one document title.');
      if (!(await page.locator('article h1').textContent()).includes(title)) throw Error('Wrong page title.');
    }

    await page.goto(base + 'index.html');
    await selected('index.html');
    if (await page.locator('.course-card').count() !== 10) throw Error('Missing lesson overview cards.');
    for (const kind of ['lesson', 'exercise', 'answers', 'code']) {
      if (await page.locator(`aside .navlink[data-kind="${kind}"]`).count() !== 10) throw Error('Missing sidebar group: ' + kind);
    }
    results.push('homepage and four independent lesson groups');
    await page.screenshot({path: path.join(output, '导航-首页.png')});

    await nav('l02-lesson.html').click();
    await independent('l02-lesson.html', 'L02');
    await page.locator('.lesson-tabs').getByRole('link', {name: '练习', exact: true}).click();
    await independent(exercise(2), 'L02 练习');
    await page.reload();
    await selected(exercise(2));
    await page.goBack();
    await selected('l02-lesson.html');
    await page.goForward();
    await selected(exercise(2));
    results.push('navigation, selected state, reload, back and forward');

    await page.locator('.lesson-tabs').getByRole('link', {name: '参考答案', exact: true}).click();
    await independent(answers(2), 'L02 参考答案');
    await nav(code(2)).click();
    await independent(code(2), '本课独立示例');
    await page.locator('.lesson-tabs').getByRole('link', {name: '讲义', exact: true}).click();
    await page.locator('.pager a[rel="next"]').click();
    await independent(code(2), '本课独立示例');
    await page.locator('.pager a[rel="next"]').click();
    await independent(exercise(2), 'L02 练习');
    await page.locator('.pager a[rel="next"]').click();
    await independent('l03-lesson.html', 'L03');
    results.push('lesson tabs and meaningful previous/next pages');

    await page.goto(base + exercise(6));
    await independent(exercise(6), 'L06 练习');
    await page.locator('#navsearch').fill('L06');
    if (await page.locator('aside .navlink:visible').count() !== 4) throw Error('Filtering should retain L06 lesson, code, exercise and answers.');
    await page.locator('#navsearch').fill('');
    const beforeTheme = await page.locator('html').getAttribute('data-theme');
    await page.locator('#theme').click();
    const afterTheme = await page.locator('html').getAttribute('data-theme');
    if (beforeTheme === afterTheme) throw Error('Theme did not toggle.');
    await nav('l06-lesson.html').click();
    await selected('l06-lesson.html');
    if (await page.locator('html').getAttribute('data-theme') !== afterTheme) throw Error('Theme did not persist between pages.');
    await page.screenshot({path: path.join(output, '导航-分课阅读.png')});
    results.push('direct page opening, grouped filtering and persistent theme');

    await page.locator('article h2').first().evaluate(element => { location.hash = element.id; });
    await selected('l06-lesson.html');
    results.push('normal in-page headings keep the current document selected');

    const oldHash = doc(course.lessons[7].folder + '/练习.md');
    await page.goto(base + encodeURIComponent('全一册.html') + '#' + oldHash);
    await selected(exercise(8), true);
    if (await page.locator('script[src],link[rel="stylesheet"]').count()) throw Error('Single-file reader should inline its own resources.');
    if (!(await page.locator('#reading-mode').getAttribute('href')).endsWith(exercise(8))) throw Error('Exit should return to current independent content.');
    const nestedHash = await page.locator('#' + oldHash + ' h2').first().getAttribute('id');
    await page.goto(base + encodeURIComponent('全一册.html') + '#' + nestedHash);
    await selected(exercise(8), true);
    await page.evaluate(id => document.getElementById(id).scrollIntoView(), doc(course.lessons[2].folder + '/讲义.md'));
    await selected('l03-lesson.html', true);
    await page.evaluate(id => document.getElementById(id).scrollIntoView(), doc(course.lessons[2].folder + '/练习.md'));
    await selected(exercise(3), true);
    await page.screenshot({path: path.join(output, '导航-全一册当前项.png')});
    await page.locator('#reading-mode').click();
    await independent(exercise(3), 'L03 练习');
    await page.goto(base + encodeURIComponent('全一册.html') + '#' + oldHash);
    await selected(exercise(8), true);
    await nav('l06-lesson.html').click();
    await independent('l06-lesson.html', 'L06');
    results.push('old continuous-reader deep link, scroll tracking and exits to real pages');

    for (const name of fs.readdirSync(path.join(root, 'docs')).filter(name => name.endsWith('.html'))) {
      const html = fs.readFileSync(path.join(root, 'docs', name), 'utf8');
      const sidebar = html.split('<aside>')[1].split('</aside>')[0];
      if ((sidebar.match(/aria-current="page"/g) || []).length !== 1) throw Error('Static current item count: ' + name);
      if (/class="navlink" href="#/.test(sidebar)) throw Error('Sidebar must open real pages: ' + name);
    }
    // Also exercise a representative independent page with JavaScript disabled.
    const noScripts = await browser.newContext({javaScriptEnabled: false});
    const plain = await noScripts.newPage();
    await plain.goto(base + exercise(4));
    if (await plain.locator('aside .navlink[aria-current="page"]').count() !== 1
        || await plain.locator('aside .navlink[aria-current="page"]').getAttribute('data-page') !== exercise(4)) throw Error('Static current item is wrong.');
    await noScripts.close();
    results.push('correct selection without JavaScript');

    await page.setViewportSize({width: 390, height: 844});
    await page.goto(base + 'l01-lesson.html');
    await selected('l01-lesson.html');
    await page.waitForFunction(() => {
      const current = document.querySelector('aside .navlink[aria-current]').getBoundingClientRect();
      const box = document.querySelector('aside nav').getBoundingClientRect();
      return current.top >= box.top && current.bottom <= box.bottom;
    });
    await page.locator('h1').scrollIntoViewIfNeeded();
    const mobile = await page.evaluate(() => ({width: innerWidth, scroll: document.documentElement.scrollWidth}));
    if (mobile.scroll > mobile.width) throw Error('Mobile horizontal overflow.');
    await page.screenshot({path: path.join(output, '导航-手机.png')});
    results.push('mobile width and selected item');
    if (pageErrors.length) throw Error(pageErrors.join('\n'));
    const report = {passed: true, checks: results, mobile, page_errors: pageErrors};
    fs.writeFileSync(path.join(output, '导航检查.json'), JSON.stringify(report, null, 2));
    console.log(JSON.stringify(report));
  } finally {
    await browser.close();
  }
})().catch(error => {console.error(error); process.exitCode = 1;});
