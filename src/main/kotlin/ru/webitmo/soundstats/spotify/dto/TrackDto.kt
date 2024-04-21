package ru.webitmo.soundstats.spotify.dto

data class TrackDto(
    var album : AlbumDto,
    var artists : List<ArtistDto>,
    var id : String,
    var name : String,
)