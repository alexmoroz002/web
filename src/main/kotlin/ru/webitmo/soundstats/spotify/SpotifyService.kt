package ru.webitmo.soundstats.spotify

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import ru.webitmo.soundstats.spotify.dto.*

@Service
class SpotifyService(private val webClient: WebClient) {
    suspend fun getWorldTop() : PlaylistDto {
        return webClient.get()
            .uri("https://api.spotify.com/v1/playlists/37i9dQZEVXbMDoHDwVN2tF")
            .retrieve()
            .awaitBody()
    }

    suspend fun getUserTopArtists() : TopArtistsDto {
        return webClient.get()
            .uri("https://api.spotify.com/v1/me/top/artists")
            .retrieve()
            .awaitBody()
    }

    suspend fun getUserTopTracks() : TopTracksDto {
        return webClient.get()
            .uri("https://api.spotify.com/v1/me/top/tracks")
            .retrieve()
            .awaitBody()
    }

    suspend fun getTracksFeatures(tracks : List<TrackDto>) : TrackFeaturesDto {
        val seed = tracks.joinToString(",") { it.id }
        println(seed)
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