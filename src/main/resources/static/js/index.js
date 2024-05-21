window.addEventListener('DOMContentLoaded', async () => {
    const overlay = document.getElementById('preloader')
    if (!overlay)
        return
    overlay.style.visibility = 'visible'
    try {
        const response = await fetch('/api/spotify/top?limit=5', {
            method: 'GET'
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
            url : item.track.url,
        }))
        createTable('top-tracks-tb', tracks)
        overlay.style.visibility = 'hidden'
    } catch (error) {
        console.log(error)
        overlay.firstElementChild.src = '/images/error.svg'
        overlay.lastElementChild.innerText = error
    }
})