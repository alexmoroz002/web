package ru.webitmo.soundstats

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.servlet.HandlerInterceptor


@Order(Ordered.HIGHEST_PRECEDENCE)
class ElapsedTimeInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val startTime = System.currentTimeMillis()
        request.setAttribute("startTime", startTime)
        println("$startTime")
        return true
    }
}