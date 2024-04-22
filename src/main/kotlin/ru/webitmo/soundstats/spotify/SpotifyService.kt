package ru.webitmo.soundstats.spotify

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import ru.webitmo.soundstats.spotify.dto.*

@Service
class SpotifyService(private val webClient: WebClient) {
    suspend fun getWorldTop(limit : Int) : PlaylistItemDto {
        return webClient.get()
            .uri("https://api.spotify.com/v1/playlists/37i9dQZEVXbMDoHDwVN2tF/tracks?limit=${limit}")
            .retrieve()
            .awaitBody()
    }

    suspend fun getUserTopArtists(limit: Int) : TopArtistsDto {
        return webClient.get()
            .uri("https://api.spotify.com/v1/me/top/artists?limit=${limit}")
            .retrieve()
            .awaitBody()
    }

    suspend fun getUserTopTracks(limit: Int) : TopTracksDto {
        return webClient.get()
            .uri("https://api.spotify.com/v1/me/top/tracks?limit=${limit}")
            .retrieve()
            .awaitBody()
    }

    suspend fun getTracksFeatures(tracks : List<TrackDto>) : TrackFeaturesDto {
        val seed = tracks.joinToString(",") { it.id }
        return webClient.get()
            .uri("https://api.spotify.com/v1/audio-features?ids=${seed}")
            .retrieve()
            .awaitBody()
    }

    suspend fun getUserRecommendations(artists : List<ArtistDto>?, tracks : List<TrackDto>?, profile : SingleTrackFeaturesDto?) : List<TrackDto> {
        TODO()
    }

    suspend fun createPlaylist(playlist : PlaylistDto) {
        TODO()
    }
}