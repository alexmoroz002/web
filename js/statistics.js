window.addEventListener('DOMContentLoaded', () => {
    plotAnalysis()
    plotGenres()
})

function plotAnalysis() {
    let target = document.getElementById('tracks-analysis-plot')
    let mockAnalysis = [0.07990286, 0.5006, 0.87115, 0.01379238, 0.08994, 0.4885]

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
            labels: ['acousticness', 'danceability', 'energy', 'instrumentalness', 'speechiness', 'valence'],
            datasets: [{
                data: mockAnalysis,
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
}

function plotGenres() {
    let mockData = ['otacore', 'russian rock', 'modern alternative rock',
        'bossbeat', 'rebel blues', 'power metal', 'anime']
    let mockCnt = ['7', '6', '5', '4', '4', '4', '3']
    let target = document.getElementById('top-genres-plot')

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
            labels: mockData,
            datasets: [{
                label: '# треков',
                data: mockCnt,
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
}