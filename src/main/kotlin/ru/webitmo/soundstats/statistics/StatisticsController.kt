package ru.webitmo.soundstats.statistics

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
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
class StatisticsController(private val service : StatisticsService) {
    @GetMapping("/artists")
    @Operation(summary = "Top artists",  description = "Retrieves authenticated User top artists",
        responses = [
            ApiResponse(description = "List of top artists"),
            ApiResponse(responseCode = "401", description = "User not authorized",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "403", description = "Not enough permissions",
                content = [Content(mediaType = "application/json")])
        ]
    )
    suspend fun userTopArtists(@RequestParam limit : Int = 5) : List<ArtistDto> {
        return service.getTopArtists(limit)
    }

    @GetMapping("/tracks")
    @Operation(summary = "Top tracks",  description = "Retrieves authenticated User top tracks",
        responses = [
            ApiResponse(description = "List of top tracks"),
            ApiResponse(responseCode = "401", description = "User not authorized",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "403", description = "Not enough permissions",
                content = [Content(mediaType = "application/json")])
        ]
    )
    suspend fun userTopTracks(@RequestParam limit : Int = 5) : List<TrackDto> {
        return service.getTopTracks(limit)
    }

    @GetMapping("/genres")
    @Operation(summary = "Top genres",  description = "Retrieves authenticated User top genres",
        responses = [
            ApiResponse(description = "Map of top genres"),
            ApiResponse(responseCode = "401", description = "User not authorized",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "403", description = "Not enough permissions",
                content = [Content(mediaType = "application/json")])
        ]
    )
    suspend fun userTopGenres() : Map<String, Int> {
        return service.getTopGenres()
    }

    @GetMapping("/profile")
    @Operation(summary = "Music profile",  description = "Retrieves authenticated User music profile",
        responses = [
            ApiResponse(description = "Music profile"),
            ApiResponse(responseCode = "401", description = "User not authorized",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "403", description = "Not enough permissions",
                content = [Content(mediaType = "application/json")])
        ]
    )
    suspend fun userMusicProfile() : SingleTrackFeaturesDto {
        return service.getTopFeatures()
    }

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientException(ex : WebClientResponseException) : ResponseEntity<String> =
        ResponseEntity<String>(ex.responseBodyAsString, ex.headers, ex.statusCode)
}