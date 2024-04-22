package ru.webitmo.soundstats.spotify

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
    suspend fun topTracks(@RequestParam limit : Int = 20) = service.getWorldTop(limit)

    @PostMapping("/playlist")
    suspend fun playlist(@RequestBody info : PlaylistInfoDto): PlaylistDto {
        val user = SecurityContextHolder.getContext().authentication.name
        return service.createPlaylist(user, info)
    }

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientException(ex : WebClientResponseException) : ResponseEntity<String> =
        ResponseEntity<String>(ex.responseBodyAsString, ex.headers, ex.statusCode)
}