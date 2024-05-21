package ru.webitmo.soundstats.spotify.dto

data class TrackDto(
    var album : AlbumDto? = null,
    var artists : List<ArtistDto> = emptyList(),
    var id : String = "",
    var name : String = "",
) {
    val url : String get() = "https://open.spotify.com/track/${id}"
    val uri : String get() = "spotify:track:${id}"
}