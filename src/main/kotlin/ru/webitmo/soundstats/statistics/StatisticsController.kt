package ru.webitmo.soundstats.statistics

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import ru.webitmo.soundstats.spotify.dto.*


@RestController
@RequestMapping("/api/statistics")
@CrossOrigin("*")
@Tag(name = "Statistics", description = "Sample OAuth methods")
@SecurityRequirement(name = "authorization")
class StatisticsController(private val webClient : WebClient, private val service : StatisticsService) {
    @Operation(summary = "User info",  description = "Retrieves information about authenticated User",
        security = [SecurityRequirement(name = "authorization", scopes = ["user-read-private"])],
        responses = [
            ApiResponse(description = "User info",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "401", description = "User not authorized",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "403", description = "Not enough permissions",
                content = [Content(mediaType = "application/json")])
        ]
    )
    @GetMapping("/me")
    fun userInfo() : Mono<String> {
        return webClient.get()
            .uri("https://api.spotify.com/v1/me")
            .retrieve()
            .bodyToMono(String::class.java)
    }

    @Operation(summary = "Top artists",  description = "Retrieves authenticated User top artists")
    @GetMapping("/artists")
    suspend fun userTopArtists() : List<ArtistDto> {
        return service.getTopArtists()
    }

    @Operation(summary = "Top tracks",  description = "Retrieves authenticated User top tracks")
    @GetMapping("/tracks")
    suspend fun userTopTracks() : List<TrackDto> {
        return service.getTopTracks()
    }

    @GetMapping("/genres")
    suspend fun userTopGenres() : Map<String, Int> {
        return service.getTopGenres()
    }

    @GetMapping("/profile")
    suspend fun userMusicProfile() : SingleTrackFeaturesDto {
        return service.getTopFeatures()
    }

//    @GetMapping("/features")
//    suspend fun feat() : TrackFeaturesDto {
//        return service.getTracksFeatures(service.getUserTopTracks().items)
//    }
//
//    @GetMapping("/track")
//    suspend fun track() : TrackDto {
//        return webClient.get()
//            .uri("https://api.spotify.com/v1/tracks/5ESoZamdGtJyucO9GHQExV?si=ccd41ad32c914bd4")
//            .retrieve()
//            .awaitBody()
//    }
//
//    @GetMapping("/playlist")
//    suspend fun playlist(@RequestParam id : String) : PlaylistDto {
//        return webClient.get()
//            .uri("https://api.spotify.com/v1/playlists/${id}")
//            .retrieve()
//            .awaitBody()
//    }
//
//    @GetMapping("/album")
//    fun album(@RequestParam id : String) : AlbumDto? {
//        return webClient.get()
//            .uri("https://api.spotify.com/v1/albums/${id}")
//            .retrieve()
//            .bodyToMono(AlbumDto::class.java)
//            .block()
//    }
//
//    @GetMapping("/artist")
//    fun artist() : ArtistDto? {
//        return webClient.get()
//            .uri("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg")
//            .retrieve()
//            .bodyToMono(ArtistDto::class.java)
//            .block()
//    }

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientException(ex : WebClientResponseException) : ResponseEntity<String> =
        ResponseEntity<String>(ex.responseBodyAsString, ex.headers, ex.statusCode)
}