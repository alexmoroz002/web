package ru.webitmo.soundstats

import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute


@ControllerAdvice
class ElapsedTimeControllerAdvice {
    @Order(Ordered.LOWEST_PRECEDENCE)
    @ModelAttribute("responseData")
    fun getCurrentUser(request: HttpServletRequest): Long {
        for (i in 1..1e100.toInt()) {

        }
        println("   ${System.currentTimeMillis()}")
        val res = System.currentTimeMillis() - request.getAttribute("startTime") as Long
        println("   ${res}")
        return res
    }
}