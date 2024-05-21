package ru.webitmo.soundstats.authorization.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SpotifyUserDto(
    @JsonProperty("display_name")
    var displayName : String,
    var id : String
)
