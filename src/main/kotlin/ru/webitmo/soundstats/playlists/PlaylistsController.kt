package ru.webitmo.soundstats.playlists

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClientResponseException
import ru.webitmo.soundstats.playlists.dto.MessageResponse
import ru.webitmo.soundstats.playlists.entities.Playlist
import ru.webitmo.soundstats.spotify.SpotifyService
import ru.webitmo.soundstats.statistics.StatisticsService

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin("*")
@Tag(name = "Playlists", description = "Modify Spotify playlists")
@SecurityRequirement(name = "authorization")
class PlaylistsController(private val service: PlaylistsService) {

    @PostMapping("/create")
    suspend fun createPlaylist(@RequestParam method : String) : ResponseEntity<MessageResponse> {
        return service.createPlaylist(method)
    }

    @PostMapping("/restore")
    suspend fun restorePlaylist(@RequestParam id : String) : ResponseEntity<MessageResponse> {
        return service.restorePlaylist(id)
    }

    @GetMapping("/playlists")
    suspend fun getAllPlaylists() : List<Playlist> {
        return service.getPlaylists(SecurityContextHolder.getContext().authentication.name)
    }

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientException(ex : WebClientResponseException) : ResponseEntity<String> =
        ResponseEntity<String>(ex.responseBodyAsString, ex.headers, ex.statusCode)
}