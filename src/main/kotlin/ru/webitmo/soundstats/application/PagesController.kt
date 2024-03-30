package ru.webitmo.soundstats.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import ru.webitmo.soundstats.application.entities.Artist
import ru.webitmo.soundstats.application.entities.Track


@Controller
class PagesController(val service: PagesService) {
    @GetMapping("")
    fun getIndex(model : Model) : String {
        return "index"
    }

    @GetMapping("/statistics")
    fun getStats(model : Model) : String {
        model["artists"] = service.getLikedArtists()
        model["tracks"] = service.getLikedTracks()
        return "statistics"
    }

    @GetMapping("/playlists")
    fun getPlaylists(model : Model) : String {
        return "playlists"
    }

}