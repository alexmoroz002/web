package ru.webitmo.soundstats.playlists.algorithms

import org.springframework.stereotype.Component
import ru.webitmo.soundstats.spotify.SpotifyService
import ru.webitmo.soundstats.spotify.dto.RecommendationsDto


@Component("artists")
class ArtistsTopCreation(private val spotifyService: SpotifyService) : PlaylistCreation {
    override suspend fun execute(): Pair<String, RecommendationsDto> {
        val topArtists = spotifyService.getUserTopArtists(10).items.shuffled().take(5)
        val recs = spotifyService.getRecommendations(20, artists = topArtists)
        val name = "Artists Top"
        return Pair(name, recs)
    }
}