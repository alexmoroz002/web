package ru.webitmo.soundstats.application

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
class NavigationControllerAdvice {
    @ModelAttribute("navigationItems")
    fun addNavigation() : List<NavigationItem> {
        return listOf(
            NavigationItem("statistics", "Статистика"),
            NavigationItem("playlists", "Плейлисты"),
        )
    }
}