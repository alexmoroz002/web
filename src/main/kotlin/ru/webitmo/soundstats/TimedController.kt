package ru.webitmo.soundstats

import org.springframework.stereotype.Controller


@Controller
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TimedController