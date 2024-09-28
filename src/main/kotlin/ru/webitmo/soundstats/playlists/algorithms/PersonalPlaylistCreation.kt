package ru.webitmo.soundstats.playlists.algorithms

import org.springframework.stereotype.Component
import ru.webitmo.soundstats.spotify.SpotifyService
import ru.webitmo.soundstats.spotify.dto.RecommendationsDto

@Component("personal")
class PersonalPlaylistCreation(private val spotifyService: SpotifyService) : PlaylistCreation {
    override suspend fun execute(): Pair<String, RecommendationsDto> {
        val userTopTracks = spotifyService.getUserTopTracks(50).items
        val tracksFeatures = spotifyService.getAverageFeatures(userTopTracks)
        val recs = spotifyService.getRecommendations(20, tracks = userTopTracks.take(20).shuffled().take(5), features = tracksFeatures)
        val name = "Personal Top"
        return Pair(name, recs)
    }
}