package ru.webitmo.soundstats.playlists.algorithms

import ru.webitmo.soundstats.spotify.dto.RecommendationsDto


interface PlaylistCreation {
    suspend fun execute() : Pair<String, RecommendationsDto>
}