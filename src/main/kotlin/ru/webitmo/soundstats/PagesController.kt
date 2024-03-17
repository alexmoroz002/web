package ru.webitmo.soundstats

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@TimedController
class PagesController {
    @GetMapping("")
    fun getIndex(model : Model) : String {
        return "index"
    }

    @GetMapping("/statistics")
    fun getStats(model : Model) : String {
        return "statistics"
    }

    @GetMapping("/playlists")
    fun getPlaylists(model : Model) : String {
        return "playlists"
    }
}