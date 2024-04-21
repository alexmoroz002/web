package ru.webitmo.soundstats.spotify.dto

data class ArtistDto(
    var name : String,
    var id : String,
    var genres : List<String> = emptyList(),
    var images : List<ImageDto> = emptyList()
)