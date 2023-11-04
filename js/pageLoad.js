window.addEventListener('DOMContentLoaded', () => {
    setActive()
})

window.addEventListener('load', () => {
    const [p] = performance.getEntriesByType("navigation")
    document.getElementById('load-time').innerHTML = (p.domComplete.toFixed()).toString() + ' '
});

function setActive() {
    let path = window.location.pathname.split("/").pop()
    let hamButtons = document.getElementsByClassName('ham-menu__link')
    for (let h of hamButtons) {
        if (h.getAttribute('href') === path)
            h.classList.add('ham-menu__item_active')
    }

    let navButtons = document.getElementsByClassName('nav-button')
    for (let b of navButtons) {
        if (b.getAttribute('href') === path)
            b.firstElementChild.classList.add('nav-button__text_active')
    }
}