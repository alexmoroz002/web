package ru.webitmo.soundstats.spotify

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClientResponseException
import ru.webitmo.soundstats.spotify.dto.PlaylistDto
import ru.webitmo.soundstats.spotify.dto.PlaylistInfoDto


@RestController
@RequestMapping("/api/spotify")
@CrossOrigin("*")
@Tag(name = "Spotify", description = "Direct Spotify endpoints")
@SecurityRequirement(name = "authorization")
class SpotifyController(private val service : SpotifyService) {
    @GetMapping("/top")
    @Operation(summary = "World Top Tracks",  description = "Retrieves world top tracks using authenticated user",
        responses = [
            ApiResponse(description = "Object holding List of tracks"),
            ApiResponse(responseCode = "401", description = "User not authorized",
                content = [Content(mediaType = "application/json")]),
            ApiResponse(responseCode = "403", description = "Not enough permissions",
                content = [Content(mediaType = "application/json")])
        ]
    )
    suspend fun topTracks(@RequestParam limit : Int = 20) = service.getWorldTop(limit)

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientException(ex : WebClientResponseException) : ResponseEntity<String> =
        ResponseEntity<String>(ex.responseBodyAsString, ex.headers, ex.statusCode)
}