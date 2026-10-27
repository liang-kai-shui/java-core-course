(() => {
  const root = document.documentElement;
  const sidebar = document.querySelector('aside');
  const links = [...document.querySelectorAll('.navlink')];
  const combined = document.body.dataset.readingMode === 'combined';
  const scrollKey = 'java-course-nav-scroll';

  try { root.dataset.theme = localStorage.getItem('java-course-theme') || 'dark'; } catch (_) {}
  document.querySelector('#theme')?.addEventListener('click', () => {
    root.dataset.theme = root.dataset.theme === 'light' ? 'dark' : 'light';
    try { localStorage.setItem('java-course-theme', root.dataset.theme); } catch (_) {}
  });

  // Every sidebar entry navigates to a real page, including from the continuous reader.
  function markCurrent(link, locationOnly = false) {
    for (const item of links) {
      if (item === link) item.setAttribute('aria-current', locationOnly ? 'location' : 'page');
      else item.removeAttribute('aria-current');
    }
  }

  function revealCurrent() {
    if (!sidebar) return;
    const scroller = matchMedia('(max-width: 800px)').matches ? sidebar.querySelector('nav') : sidebar;
    if (!scroller) return;
    const current = sidebar.querySelector('[aria-current]');
    if (!current || current.hidden) return;
    const box = scroller.getBoundingClientRect(), item = current.getBoundingClientRect();
    if (item.top < box.top + 12 || item.bottom > box.bottom - 12) {
      scroller.scrollTop += item.top - box.top - scroller.clientHeight / 2 + item.height / 2;
    }
  }

  function rememberSidebar() {
    try { if (sidebar) sessionStorage.setItem(scrollKey, String(sidebar.scrollTop)); } catch (_) {}
  }
  try { if (sidebar) sidebar.scrollTop = Number(sessionStorage.getItem(scrollKey)) || 0; } catch (_) {}
  sidebar?.addEventListener('scroll', rememberSidebar, { passive: true });
  window.addEventListener('pagehide', rememberSidebar);
  window.addEventListener('resize', () => requestAnimationFrame(revealCurrent));
  for (const link of links) {
    link.addEventListener('click', event => {
      if (event.defaultPrevented || event.button !== 0 || event.ctrlKey || event.metaKey || event.shiftKey || event.altKey) return;
      markCurrent(link);
      rememberSidebar();
    });
  }

  const input = document.querySelector('#navsearch');
  input?.addEventListener('input', () => {
    const query = input.value.trim().toLocaleLowerCase();
    for (const link of links) {
      link.hidden = !link.textContent.toLocaleLowerCase().includes(query);
    }
  });
  for (const table of document.querySelectorAll('table')) {
    if (table.parentElement.classList.contains('table-wrap')) continue;
    const wrapper = document.createElement('div');
    wrapper.className = 'table-wrap';
    table.replaceWith(wrapper);
    wrapper.appendChild(table);
  }

  if (combined) {
    const sections = [...document.querySelectorAll('.bundle-section')];
    const exit = document.querySelector('#reading-mode');
    let previous = null, scheduled = false;
    function activateSection(section) {
      if (!section) return;
      const link = links.find(item => item.dataset.doc === section.dataset.navDoc)
        || links.find(item => item.dataset.page === '全一册.html');
      if (link !== previous) {
        markCurrent(link, true);
        previous = link;
        revealCurrent();
      }
      if (exit) exit.href = section.dataset.page;
    }
    function fromViewport() {
      scheduled = false;
      // Native anchor jumps honor scroll-margin-top; sample just below that line
      // so a deep-linked section cannot be mistaken for its preceding document.
      const anchorOffset = sections[0] ? parseFloat(getComputedStyle(sections[0]).scrollMarginTop) || 0 : 0;
      const readingLine = Math.max((document.querySelector('.top')?.getBoundingClientRect().bottom || 0) + 24, anchorOffset + 4);
      let current = sections[0];
      for (const section of sections) {
        if (section.getBoundingClientRect().top <= readingLine) current = section;
        else break;
      }
      activateSection(current);
    }
    function scheduleViewport() {
      if (!scheduled) { scheduled = true; requestAnimationFrame(fromViewport); }
    }
    function fromHash() {
      let hash;
      try { hash = decodeURIComponent(location.hash.slice(1)); } catch (_) { hash = ''; }
      const target = hash && document.getElementById(hash);
      if (target) activateSection(target.closest('.bundle-section'));
      scheduleViewport();
    }
    window.addEventListener('scroll', scheduleViewport, { passive: true });
    window.addEventListener('resize', scheduleViewport);
    window.addEventListener('hashchange', fromHash);
    window.addEventListener('pageshow', fromHash);
    window.addEventListener('load', fromHash);
    fromHash();
  } else {
    // The generated HTML already has the right state, even without JavaScript.
    // Reconcile it on history restoration, without treating a heading hash as a new page.
    function syncPage() {
      const link = links.find(item => item.dataset.doc === document.body.dataset.navDoc);
      markCurrent(link);
      requestAnimationFrame(revealCurrent);
    }
    window.addEventListener('pageshow', syncPage);
    syncPage();
  }
})();
