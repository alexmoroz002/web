function createTable(id, elements) {
    const tableBody = document.getElementById(id)
    if (!tableBody)
        return
    elements.forEach((track) => {
        const row = document.createElement('tr')
        row.classList.add('table__row')

        if (track.index !== undefined) {
            const index = document.createElement('td')
            index.classList.add('table__cell', 'table__index')
            index.innerText = track.index.toString()
            row.appendChild(index)
        }

        const coverContainer = document.createElement('td')
        coverContainer.classList.add('table__cell', 'table__cover')
        const cover = document.createElement('img')
        cover.classList.add('table__cover-img')
        cover.src = track.cover
        cover.alt = 'cover'
        coverContainer.appendChild(cover)
        row.appendChild(coverContainer)

        const info = document.createElement('td')
        info.classList.add('table__cell', 'table__info')
        const name = document.createElement('p')
        name.classList.add('table__info-primary')
        name.innerText = track.name
        info.appendChild(name)
        if (track.artists !== undefined) {
            const artists = document.createElement('p')
            artists.classList.add('table__info-secondary')
            artists.innerText = track.artists.join(', ')
            info.appendChild(artists)
        }
        row.appendChild(info)

        const linkContainer = document.createElement('td')
        linkContainer.classList.add('table__cell', 'table__link')
        const link = document.createElement('a')
        link.classList.add('button', 'button_primary', 'button_iconed')
        link.href = track.url
        const linkIcon = document.createElement('img')
        linkIcon.classList.add('button__icon')
        linkIcon.src = 'images/spotify_icon_alt.svg'
        linkIcon.alt = 'icon'
        const linkText = document.createElement('span')
        linkText.classList.add('button__text')
        linkText.innerText = 'Открыть в Spotify'
        link.appendChild(linkIcon)
        link.appendChild(linkText)
        linkContainer.appendChild(link)
        row.appendChild(linkContainer)

        tableBody.appendChild(row)
    })
}