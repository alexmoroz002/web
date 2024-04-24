package ru.webitmo.soundstats.application

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.reactive.function.client.WebClientResponseException
import ru.webitmo.soundstats.playlists.PlaylistsService
import ru.webitmo.soundstats.statistics.StatisticsService


@Controller
class PagesController(val playlistsService: PlaylistsService) {
    @GetMapping("")
    suspend fun getIndex(model : Model) : String {
        return "index"
    }

    @GetMapping("/statistics")
    suspend fun getStats(model : Model) : String {
        return "statistics"
    }

    @GetMapping("/playlists")
    suspend fun getPlaylists(model : Model) : String {
        return "playlists"
    }

    @GetMapping("/me")
    suspend fun getProfile(model : Model) : String {
        val user = SecurityContextHolder.getContext().authentication.name
        model.addAttribute("playlists", playlistsService.getPlaylists(user).asReversed())
        return "me"
    }

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientException(ex : WebClientResponseException) : ResponseEntity<String> =
        ResponseEntity<String>(ex.responseBodyAsString, ex.headers, ex.statusCode)
}