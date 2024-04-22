package ru.webitmo.soundstats.spotify.dto

data class PlaylistInfoDto(
    var name : String,
    var desc : String = "",
    var public : Boolean = false
)
