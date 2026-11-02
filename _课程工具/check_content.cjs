/* Verify actual code rendering and layout for every authored learning unit. */
const {chromium} = require('playwright');
const fs = require('fs');
const path = require('path');
const {pathToFileURL} = require('url');
const root = path.resolve(__dirname, '..');
const course = JSON.parse(fs.readFileSync(path.join(__dirname, 'course.json'), 'utf8'));
const base = process.env.COURSE_BASE_URL || pathToFileURL(path.join(root, 'docs', 'index.html')).href.replace(/index\.html$/, '');
const output = path.join(root, 'outputs');
fs.mkdirSync(output, {recursive:true});
const normalize = text => text.replace(/\r\n/g, '\n').trim();

(async () => {
  const browser = await chromium.launch({headless:true, ...(process.env.COURSE_BROWSER ? {executablePath:process.env.COURSE_BROWSER} : {})});
  const context = await browser.newContext({viewport:{width:1440,height:1000}});
  const page = await context.newPage();
  const errors=[];
  page.on('pageerror', error=>errors.push(error.message));
  const results=[];
  try {
    await page.goto(base+'index.html');
    if (await page.locator('.course-card').count() !== course.lessons.length) throw Error('Home count mismatch');
    for (const unit of course.lessons.filter(item=>item.id.startsWith('S'))) {
      if (!(await page.locator('.start-links a').allTextContents()).some(text=>text.includes(unit.id))) throw Error('Missing structure shortcut: '+unit.id);
    }
    await page.screenshot({path:path.join(output,'结构重构-首页.png')});
    for (const unit of course.lessons) {
      const text=fs.readFileSync(path.join(root,unit.folder,'讲义.md'),'utf8');
      const expected=[...text.matchAll(/^```(?:java|java-source)\s*\n(.*?)^```/gms)].map(match=>normalize(match[1]));
      const filename=unit.id.toLowerCase()+'-lesson.html';
      await page.setViewportSize({width:1440,height:1000});
      await page.goto(base+filename);
      if (await page.locator('article h1').count()!==1) throw Error('Document hierarchy: '+unit.id);
      const actual=(await page.locator('pre code.language-java,pre code.language-java-source').allTextContents()).map(normalize);
      if (JSON.stringify(actual)!==JSON.stringify(expected)) throw Error('Rendered source mismatch: '+unit.id);
      const body=await page.locator('article').innerText();
      for (const marker of [`书本 p${unit.book_start}–${unit.book_end}`,`PDF p${unit.pdf_start}–${unit.pdf_end}`]) {
        if (!body.includes(marker)) throw Error('Missing page mapping: '+unit.id);
      }
      if (/[\u0000-\u0008\u000b\u000c\u000e-\u001f]/.test(body)) throw Error('Invisible text corruption: '+unit.id);
      const active=page.locator('aside .navlink[aria-current]');
      if (await active.count()!==1 || await active.getAttribute('data-page')!==filename) throw Error('Wrong selected unit: '+unit.id);
      const captures={S01:'亲手让程序',S02:'先画关系',S03:'参数是',S04:'先看项目目录',S05:'打开箱子',S06:'一张职责地图',L02:'亲手编译'};
      if (captures[unit.id]) {
        await page.getByRole('heading',{name:new RegExp(captures[unit.id])}).first().scrollIntoViewIfNeeded();
        await page.screenshot({path:path.join(output,'结构重构-'+unit.id+'.png')});
      }
      await page.setViewportSize({width:390,height:844});
      const sizes=await page.evaluate(()=>({width:innerWidth,scroll:document.documentElement.scrollWidth}));
      if (sizes.scroll>sizes.width) throw Error('Mobile overflow: '+unit.id);
      if (unit.id==='S02') {
        await page.getByRole('heading',{name:/先画关系/}).scrollIntoViewIfNeeded();
        await page.screenshot({path:path.join(output,'结构重构-手机.png')});
      }
      results.push({id:unit.id,code_blocks:actual.length,mobile:sizes,passed:true});
    }
    if (errors.length) throw Error(errors.join('\n'));
    const report={passed:true,base_url:base,units:results.length,rendered_java_blocks:results.reduce((n,x)=>n+x.code_blocks,0),results,page_errors:errors};
    fs.writeFileSync(path.join(output,'结构重构内容检查.json'),JSON.stringify(report,null,2));
    console.log(JSON.stringify({passed:true,units:report.units,rendered_java_blocks:report.rendered_java_blocks}));
  } finally {await browser.close();}
})().catch(error=>{console.error(error);process.exitCode=1;});
