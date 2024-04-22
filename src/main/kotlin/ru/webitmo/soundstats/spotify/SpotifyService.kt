package ru.webitmo.soundstats.spotify

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import ru.webitmo.soundstats.spotify.dto.PlaylistInfoDto
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

    suspend fun getRecommendations(limit : Int, artists : List<ArtistDto> = emptyList(), tracks : List<TrackDto> = emptyList(), features : SingleTrackFeaturesDto? = null) : RecommendationsDto {
        var baseUri = "https://api.spotify.com/v1/recommendations?limit=${limit}"
        val seedArtists = artists.map { it.id }.shuffled().take(3)
        if (seedArtists.isNotEmpty())
            baseUri += "&seed_artists=${seedArtists.joinToString(",")}"
        val seedTracks = tracks.map { it.id }.shuffled().take(5 - seedArtists.size)
        if (seedTracks.isNotEmpty())
            baseUri += "&seed_tracks=${seedTracks.joinToString(",")}"
        if (features != null)
            baseUri += "&target_acousticness=${features.acousticness}&target_danceability=${features.danceability}&target_energy=${features.energy}" +
                    "&target_instrumentalness=${features.instrumentalness}&target_speechiness=${features.speechiness}&target_valence=${features.valence}"
        return webClient.get()
            .uri(baseUri)
            .retrieve()
            .awaitBody()
    }

    suspend fun createPlaylist(user : String, info : PlaylistInfoDto) : PlaylistDto {
        val body = mapOf(Pair("name", info.name), Pair("description", info.desc), Pair("public", info.public))
        return webClient.post()
            .uri("https://api.spotify.com/v1/users/${user}/playlists")
            .body(BodyInserters.fromValue(body))
            .retrieve()
            .awaitBody()
    }

    suspend fun addItemsToPlaylist(playlist : PlaylistDto, tracks : List<TrackDto>) {
        val uri = "https://api.spotify.com/v1/playlists/${playlist.id}/tracks?uris=${tracks.joinToString(",") { it.uri }}"
        println(uri)
        webClient.post()
            .uri(uri)
            .retrieve()
            .awaitBody<Any>()
    }
}
