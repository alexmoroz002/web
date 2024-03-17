window.addEventListener('DOMContentLoaded', () => {
    setActive()
})

window.addEventListener('load', () => {
    const [p] = performance.getEntriesByType("navigation")
    document.getElementById('load-time').innerHTML = (p.domInteractive.toFixed() - p.responseEnd.toFixed()).toString()
});

function setActive() {
    let path = window.location.pathname.split("/").pop()
    let hamButton = document.querySelector(".ham-menu__link[href='" + CSS.escape(path) + "']")
    if (hamButton)
        hamButton.classList.add('ham-menu__item_active')

    let navButton = document.querySelector(".nav-button[href='" + CSS.escape(path) + "']")
    if (navButton)
        navButton.firstElementChild.classList.add('nav-button__text_active')
}
