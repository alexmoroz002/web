window.addEventListener('DOMContentLoaded', async () => {
    // Токен действует 1 час после получения, к моменту проверки он скорее всего истек (:
    const token = "BQBQprZ5ACzRhUomvzgs8XnrqRRPJrCcleygRI4UJXSwEVUxRSeSDL7IukVte1p8RacfgcL-wJH_0VsGpVd35flpks64Q9otmzj5VegtG9ftixcq0yw"
    const table = document.getElementById('top-tracks-table')
    const overlay = document.getElementById('preloader')
    if (!table || !overlay)
        return
    table.classList.add('table_loading')
    overlay.style.visibility = 'visible'
    try {
        const response = await fetch('https://api.spotify.com/v1/playlists/37i9dQZEVXbNG2KDcFcKOF/tracks?market=BR&limit=5', {
            method: 'GET',
            headers: {
                Authorization: 'Bearer ' + token
            }
        });
        const responseData = await response.json();
        if (!response.ok)
            throw new Error(`${responseData.error.status} ${responseData.error.message}`);
        let cnt = 1;
        const tracks = responseData.items.map((item) => ({
            index : cnt++,
            name: item.track.name,
            cover : (item.track.album.images[0]).url,
            artists : item.track.artists.map((art) => {return art.name}),
            url : item.track.external_urls.spotify,
        }))
        const tableBody = document.getElementById('top-tracks-tb')
        if (!tableBody)
            return
        tracks.forEach((track) => {
            const row = document.createElement('tr')
            row.classList.add('table__row')

            const index = document.createElement('td')
            index.classList.add('table__cell', 'table__index')
            index.innerText = track.index.toString()
            row.appendChild(index)

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
            const artists = document.createElement('p')
            artists.classList.add('table__info-secondary')
            artists.innerText = track.artists.join(', ')
            info.appendChild(name)
            info.appendChild(artists)
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
        overlay.style.visibility = 'hidden'
        table.classList.remove('table_loading')
    } catch (error) {
        console.log(error)
        overlay.firstElementChild.src = '/images/error.svg'
        overlay.lastElementChild.innerText = error
        table.classList.add('table_error')
    }
})