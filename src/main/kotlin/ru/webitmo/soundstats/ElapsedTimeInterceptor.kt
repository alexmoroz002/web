package ru.webitmo.soundstats

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView


@Order(Ordered.HIGHEST_PRECEDENCE)
class ElapsedTimeInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val startTime = System.currentTimeMillis()
        request.setAttribute("startTime", startTime)
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        val time = request.getAttribute("startTime") ?: -1L
        val res = System.currentTimeMillis() - time as Long
        modelAndView?.addObject("responseData", res)
    }
}