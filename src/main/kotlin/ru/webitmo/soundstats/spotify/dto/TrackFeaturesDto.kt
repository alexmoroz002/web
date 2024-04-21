package ru.webitmo.soundstats.spotify.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SingleTrackFeaturesDto(
    var acousticness : Float,
    var danceability : Float,
    var energy : Float,
    var id : String,
    var instrumentalness : Float,
    var liveness : Float,
    var loudness : Float,
    var speechiness : Float,
    var tempo : Float,
    @JsonProperty("time_signature") var timeSignature : Int,
    var uri : String,
    var valence : Float
)

data class TrackFeaturesDto(
    @JsonProperty("audio_features") var data : List<SingleTrackFeaturesDto>
)