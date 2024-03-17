window.addEventListener('DOMContentLoaded', () => {
    setActive()
})

window.addEventListener('load', () => {
    const [p] = performance.getEntriesByType("navigation")
    let time = document.getElementById('server-time').textContent
    document.getElementById('load-time').innerHTML = (p.domComplete.toFixed() - time).toString()
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
