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


@RestController
@RequestMapping("/api/protected")
@CrossOrigin("*")
@Tag(name = "Content", description = "Sample OAuth methods")
@SecurityRequirement(name = "authorization")
class StatisticsController(private val webClient: WebClient) {
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

    @Operation(summary = "Top artists",  description = "Retrieves authenticated User top artists",
        security = [SecurityRequirement(name = "authorization", scopes = ["user-top-read"])],
        responses = [
            ApiResponse(description = "Artists list",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "401", description = "User not authorized",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "403", description = "Not enough permissions",
                content = [Content(mediaType = "application/json")])
        ]
    )
    @GetMapping("/artists")
    fun topArtists() : Mono<String> {
        return webClient.get()
            .uri("https://api.spotify.com/v1/me/top/artists")
            .retrieve()
            .bodyToMono(String::class.java)
    }

    @Operation(summary = "Top tracks",  description = "Retrieves authenticated User top tracks",
        security = [SecurityRequirement(name = "authorization", scopes = ["user-top-read"])],
        responses = [
            ApiResponse(description = "Top list",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "401", description = "User not authorized",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "403", description = "Not enough permissions",
                content = [Content(mediaType = "application/json")])
        ]
    )
    @GetMapping("/tracks")
    fun topTracks() : Mono<String> {
        return webClient.get()
            .uri("https://api.spotify.com/v1/me/top/tracks")
            .retrieve()
            .bodyToMono(String::class.java)
    }

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientException(ex : WebClientResponseException) : ResponseEntity<String> =
        ResponseEntity<String>(ex.responseBodyAsString, ex.headers, ex.statusCode)
}