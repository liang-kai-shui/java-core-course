
(()=>{const root=document.documentElement;try{root.dataset.theme=localStorage.getItem('java-course-theme')||'dark'}catch(e){}
document.querySelector('#theme').addEventListener('click',()=>{root.dataset.theme=root.dataset.theme==='light'?'dark':'light';try{localStorage.setItem('java-course-theme',root.dataset.theme)}catch(e){}});
const input=document.querySelector('#navsearch');if(input)input.addEventListener('input',()=>{const q=input.value.trim().toLowerCase();document.querySelectorAll('.navlink').forEach(a=>{a.hidden=!a.textContent.toLowerCase().includes(q);a.style.display=a.hidden?'none':''})});
document.querySelectorAll('table').forEach(table=>{const box=document.createElement('div');box.className='table-wrap';table.replaceWith(box);box.appendChild(table)});
})();
