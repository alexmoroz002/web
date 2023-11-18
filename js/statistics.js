window.addEventListener('DOMContentLoaded', () => {
    plotAnalysis()
    plotGenres()
})

function plotAnalysis1(){
    let targetDiv = document.getElementById('tracks-analysis-plot')
    const data = [{
        type: 'scatterpolar',
        theta: ['acousticness', 'danceability', 'energy', 'instrumentalness', 'speechiness', 'valence'],
        r: [0.07990286, 0.5006, 0.87115, 0.01379238, 0.08994, 0.4885],
        fill: 'toself'
    }]
    const layout = {
        polar: {
            radialaxis: {
                visible: true,
                range: [0, 1]
            },
            angularaxis: {
                rotation: 60
            },
            bgcolor: '#e7f5fd'
        },
        showlegend: false,
        paper_bgcolor: "rgba(0,0,0,0)"
    }
    const config = {responsive: true}

    Plotly.newPlot(targetDiv, data, layout, config)
}

function plotAnalysis() {
    let target = document.getElementById('tracks-analysis-plot')
    let mockAnalysis = [0.07990286, 0.5006, 0.87115, 0.01379238, 0.08994, 0.4885]

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

function plotGenres1() {
    let mockData = ['otacore', 'otacore', 'otacore', 'otacore', 'otacore', 'otacore',
        'otacore', 'modern alternative rock', 'russian rock',
        'rebel blues', 'rebel blues', 'russian rock',
        'russian rock', 'russian rock',
        'modern alternative rock', 'rebel blues',
        'modern alternative rock', 'rebel blues', 'rebel blues',
        'modern alternative rock', 'russian rock',
        'modern alternative rock', 'bossbeat', 'bossbeat', 'bossbeat',
        'bossbeat', 'anime', 'anime', 'anime']
    let targetDiv = document.getElementById('top-genres-plot')
    const data = [{
        type: 'histogram',
        x: mockData
    }]
    const layout = {
        margin: {
            l: 30,
            r: 30,
            b: 30,
            t: 30,
            pad: 10
        },
        xaxis: {
            automargin: true
        },
        paper_bgcolor: "rgba(0,0,0,0)",
        plot_bgcolor: '#e7f5fd'
    }
    const config = {responsive: true}
    Plotly.newPlot(targetDiv, data, layout, config)
}

function plotGenres() {
    let mockData = ['otacore', 'modern alternative rock', 'russian rock', 'rebel blues', 'bossbeat', 'anime']
    let mockCnt = [7, 5, 5, 5, 4, 3]
    let target = document.getElementById('top-genres-plot')

    new Chart(target, {
        type: 'bar',
        data: {
            labels: mockData,
            datasets: [{
                label: '# треков',
                data: mockCnt,
                borderWidth: 2,
            }]
        },
        options: {
            layout: {
                padding: 20
            },
            scales: {
                x: {
                    ticks: {
                        font: {
                            size: 12,
                            weight: 500
                        },
                    }
                },
                y: {
                    ticks: {
                        font: {
                            size: 12,
                            weight: 500
                        }
                    }
                }
            },
            maintainAspectRatio: false
        }
    })
}