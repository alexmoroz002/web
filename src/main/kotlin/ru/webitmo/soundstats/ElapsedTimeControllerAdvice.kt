package ru.webitmo.soundstats

import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute


@Order(Ordered.LOWEST_PRECEDENCE)
@ControllerAdvice(annotations = [TimedController::class])
class ElapsedTimeControllerAdvice {
    @ModelAttribute("responseData")
    fun getCurrentUser(request: HttpServletRequest): Long {
        val res = System.currentTimeMillis() - request.getAttribute("startTime") as Long
        return res
    }
}