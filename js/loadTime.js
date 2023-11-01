window.addEventListener('load', () => {
    const [p] = performance.getEntriesByType("navigation");
    document.getElementById('load-time').innerHTML = (p.domComplete.toFixed()).toString() + ' '
});