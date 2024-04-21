package ru.webitmo.soundstats.spotify.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SingleTrackFeaturesDto(
    var acousticness : Double,
    var danceability : Double,
    var instrumentalness : Double,
    var liveness : Double,
    var loudness : Double,
    var speechiness : Double,
    var tempo : Double,
    @JsonProperty("time_signature") var timeSignature : Int,
    var valence : Double,
    var energy : Double,
    var id : String = "",
)

data class TrackFeaturesDto(
    @JsonProperty("audio_features") var data : List<SingleTrackFeaturesDto>
)