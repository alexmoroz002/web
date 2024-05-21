package ru.webitmo.soundstats.spotify.dto

data class PlaylistDto(
    var name : String,
    var description : String,
    var id : String,
    var images : List<ImageDto>,
    var tracks : PlaylistItemDto,
) {
    val url : String get() = "https://open.spotify.com/playlist/${id}"
}

data class PlaylistItemDto(
    var items : List<PlaylistInnerDto>
)

data class PlaylistInnerDto(
    var track : TrackDto
)