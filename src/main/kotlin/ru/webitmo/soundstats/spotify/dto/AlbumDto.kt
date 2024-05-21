package ru.webitmo.soundstats.spotify.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AlbumDto(
    @JsonProperty("total_tracks") var size : Int,
    var id : String,
    var images : List<ImageDto>,
    var name : String,
    var genres : List<String> = emptyList(),
    var artists : List<ArtistDto>
)