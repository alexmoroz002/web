import Plotly from 'plotly.js-dist-min'

const data = [{
    type: 'scatterpolar',
    theta: ['acousticness', 'danceability', 'energy', 'instrumentalness', 'speechiness', 'valence'],
    r: [0.0305, 0.308, 0.97, 0, 0.145, 0.29],
    fill: 'toself'
}]

const layout = {
    polar: {
        radialaxis: {
            visible: true,
            range: [0, 1]
        }
    },
    showlegend: false
}