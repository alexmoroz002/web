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
    fun addTimeHeader(request: HttpServletRequest): String {
        val time = request.getAttribute("startTime") ?: return ""
        val res = System.currentTimeMillis() - time as Long
        return res.toString()
    }
}