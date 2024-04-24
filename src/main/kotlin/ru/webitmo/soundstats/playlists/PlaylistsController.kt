package ru.webitmo.soundstats.playlists

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClientResponseException
import ru.webitmo.soundstats.spotify.SpotifyService
import ru.webitmo.soundstats.spotify.dto.RecommendationsDto
import ru.webitmo.soundstats.statistics.StatisticsService

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin("*")
@Tag(name = "Playlists", description = "Edit Spotify playlists")
@SecurityRequirement(name = "authorization")
class PlaylistsController(private val service: PlaylistsService, private val spotifyService: SpotifyService, private val statisticsService: StatisticsService) {
    @PostMapping("/create/world")
    suspend fun createPlaylistWorld() : ResponseEntity<String> {
        return service.createWorldTopPlaylist()
    }
    @PostMapping("/create/personal")
    suspend fun createPlaylistPersonal() : ResponseEntity<String> {
        return service.createFeaturesPlaylist()
    }

    @PostMapping("/create/artists")
    suspend fun createPlaylistArtists() : ResponseEntity<String> {
        return service.createArtistsPlaylist()
    }

    @GetMapping("/recs")
    suspend fun recs(@RequestParam limit : Int) : RecommendationsDto {
        return spotifyService.getRecommendations(limit, features = statisticsService.getTopFeatures(), artists = statisticsService.getTopArtists(), tracks = statisticsService.getTopTracks())
    }

    @PostMapping("/restore")
    suspend fun restorePlaylist(@RequestParam id : String) {
        service.restorePlaylist(id)
    }

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientException(ex : WebClientResponseException) : ResponseEntity<String> =
        ResponseEntity<String>(ex.responseBodyAsString, ex.headers, ex.statusCode)
}