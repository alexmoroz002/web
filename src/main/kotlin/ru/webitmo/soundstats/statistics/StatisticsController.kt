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
@Tag(name = "Statistics", description = "Get Spotify statistics of authenticated user")
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
    suspend fun userTopArtists(@RequestParam limit : Int = 5) : List<ArtistDto> {
        return service.getTopArtists(limit)
    }

    @Operation(summary = "Top tracks",  description = "Retrieves authenticated User top tracks")
    @GetMapping("/tracks")
    suspend fun userTopTracks(@RequestParam limit : Int = 5) : List<TrackDto> {
        return service.getTopTracks(limit)
    }

    @GetMapping("/genres")
    suspend fun userTopGenres() : Map<String, Int> {
        return service.getTopGenres()
    }

    @GetMapping("/profile")
    suspend fun userMusicProfile() : SingleTrackFeaturesDto {
        return service.getTopFeatures()
    }

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientException(ex : WebClientResponseException) : ResponseEntity<String> =
        ResponseEntity<String>(ex.responseBodyAsString, ex.headers, ex.statusCode)
}