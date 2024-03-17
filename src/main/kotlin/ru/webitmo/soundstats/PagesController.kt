package ru.webitmo.soundstats

import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class PagesController {
    @GetMapping("")
    fun getIndex(model : Model) : String {
        model.addAttribute("time1", 123)
        return "index"
    }

    @GetMapping("/statistics")
    fun getStats(model : Model) : String {
        model.addAttribute("time1", 123)
        return "statistics"
    }

    @GetMapping("/playlists")
    fun getPlaylists(model : Model) : String {
        model.addAttribute("time1", 123)
        return "playlists"
    }

//    @ModelAttribute("responseData")
//    fun responseData(response: HttpServletResponse): String {
//        println(response.headerNames)
//        return "12"
//    }

    @GetMapping("/asad")
    fun dof(): String {
        return "kjnmsf"
    }
}