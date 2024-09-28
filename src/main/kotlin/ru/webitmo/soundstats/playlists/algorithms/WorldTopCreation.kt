package ru.webitmo.soundstats.playlists.algorithms

import org.springframework.stereotype.Component
import ru.webitmo.soundstats.spotify.SpotifyService
import ru.webitmo.soundstats.spotify.dto.RecommendationsDto

@Component("world")
class WorldTopCreation(private val spotifyService: SpotifyService) : PlaylistCreation {
    override suspend fun execute(): Pair<String, RecommendationsDto> {
        val topTracks = spotifyService.getWorldTop(20).items.map { it.track }
        val recs = spotifyService.getRecommendations(20, tracks = topTracks.shuffled().take(5))
        val name = "World Top"
        return Pair(name, recs)
    }
}