window.addEventListener('DOMContentLoaded', async () => {
    await Promise.all([plotAnalysis(),
        plotGenres(),
        loadTracks(),
        loadArtists()])
})

async function plotAnalysis() {
    let target = document.getElementById('tracks-analysis-plot')
    try {
        const response = await fetch('/api/statistics/profile', {
            method: 'GET'
        });
        const responseData = await response.json();
        if (!response.ok)
            throw new Error(`${responseData.error.status} ${responseData.error.message}`);
        const analysis = Object.values(responseData)
        const labels = Object.keys(responseData)

        // let mockAnalysis = [0.07990286, 0.5006, 0.87115, 0.01379238, 0.08994, 0.4885]
        // labels: ['acousticness', 'danceability', 'energy', 'instrumentalness', 'speechiness', 'valence'],

        /**
         * Создает график Radar для отображения анализа пользователя
         *
         * @param item HTML элемент для вставки графика
         * @param config Параметры графика
         *        config.type Тип
         *        config.data Данные
         *        config.options Параметры отображения
         *
         * @returns chart График
         */
        new Chart(target, {
            type: 'radar',
            data: {
                labels: labels,
                datasets: [{
                    data: analysis,
                }]
            },
            options: {
                layout: {
                    padding: 20
                },
                plugins: {
                    legend: {
                        display: false
                    }
                },
                scales: {
                    r: {
                        pointLabels: {
                            font: {
                                size: 11,
                                weight: 500
                            }
                        },
                        ticks: {
                            backdropColor: 'rgba(255, 255, 255, 0.3)',
                        }
                    }
                },
                maintainAspectRatio: false
            }
        })
    } catch (error) {
        console.log(error)
    }
}

async function plotGenres() {
    let target = document.getElementById('top-genres-plot')
    try {
        const response = await fetch('/api/statistics/genres', {
            method: 'GET'
        });
        const responseData = await response.json();
        if (!response.ok)
            throw new Error(`${responseData.error.status} ${responseData.error.message}`);
        const genres = Object.keys(responseData)
        const cnt = Object.values(responseData)
        /**
         * Создает кольцевую диаграмму для отображения популярных жанров у пользователя
         *
         * @param item HTML элемент для вставки графика
         * @param config Параметры графика
         *        config.type Тип
         *        config.data Данные
         *        config.options Параметры отображения
         *
         * @returns chart График
         */
        new Chart(target, {
            type: 'doughnut',
            data: {
                labels: genres,
                datasets: [{
                    label: '# треков',
                    data: cnt,
                }]
            },
            options: {
                layout: {
                    padding: 20
                },
                plugins: {
                    colors: {
                        forceOverride: true
                    }
                },
                maintainAspectRatio: false
            }
        })
    } catch (error) {
        console.log(error)
    }
}

async function loadTracks()  {
    const overlay = document.getElementById('preloader-tracks')
    if (!overlay)
        return
    overlay.style.visibility = 'visible'
    try {
        const response = await fetch('/api/statistics/tracks', {
            method: 'GET'
        });
        const responseData = await response.json();
        if (!response.ok)
            throw new Error(`${responseData.error.status} ${responseData.error.message}`);
        let cnt = 1;
        const tracks = responseData.map((item) => ({
            index : cnt++,
            name: item.name,
            cover : (item.album.images[0]).url,
            artists : item.artists.map((art) => {return art.name}),
            url : item.url,
        }))
        createTable('user-top-tracks', tracks)
        overlay.style.visibility = 'hidden'
    } catch (error) {
        console.log(error)
        overlay.firstElementChild.src = '/images/error.svg'
        overlay.lastElementChild.innerText = error
    }
}

async function loadArtists()  {
    const overlay = document.getElementById('preloader-artists')
    if (!overlay)
        return
    overlay.style.visibility = 'visible'
    try {
        const response = await fetch('/api/statistics/artists', {
            method: 'GET'
        });
        const responseData = await response.json();
        if (!response.ok)
            throw new Error(`${responseData.error.status} ${responseData.error.message}`);
        let cnt = 1;
        const tracks = responseData.map((item) => ({
            name: item.name,
            cover : (item.images[0]).url,
            url : item.url,
        }))
        createTable('user-top-artists', tracks)
        overlay.style.visibility = 'hidden'
    } catch (error) {
        console.log(error)
        overlay.firstElementChild.src = '/images/error.svg'
        overlay.lastElementChild.innerText = error
    }
}