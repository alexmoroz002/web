package ru.webitmo.soundstats.spotify.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SingleTrackFeaturesDto(
    var acousticness : Double,
    var danceability : Double,
    var energy : Double,
    var instrumentalness : Double,
    var speechiness : Double,
    var valence : Double,
)

data class TrackFeaturesDto(
    @JsonProperty("audio_features") var data : List<SingleTrackFeaturesDto>
)