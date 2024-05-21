package ru.webitmo.soundstats.statistics

import org.springframework.stereotype.Service
import ru.webitmo.soundstats.spotify.SpotifyService
import ru.webitmo.soundstats.spotify.dto.ArtistDto
import ru.webitmo.soundstats.spotify.dto.SingleTrackFeaturesDto
import ru.webitmo.soundstats.spotify.dto.TrackDto

@Service
class StatisticsService(val spotifyService: SpotifyService) {
    suspend fun getTopTracks(limit : Int = 5) : List<TrackDto> {
        return spotifyService.getUserTopTracks(limit).items
    }

    suspend fun getTopArtists(limit: Int = 5) : List<ArtistDto> {
        return spotifyService.getUserTopArtists(limit).items
    }

    suspend fun getTopGenres() : Map<String, Int> {
        val artists = spotifyService.getUserTopArtists(50).items
        return artists.map { it.genres }.flatten()
            .groupingBy { it }.eachCount()
            .toList().sortedByDescending { it.second }.take(6)
            .toMap()
    }

    suspend fun getTopFeatures() : SingleTrackFeaturesDto {
        val tracks = spotifyService.getUserTopTracks(50).items
        return spotifyService.getAverageFeatures(tracks)
    }
}