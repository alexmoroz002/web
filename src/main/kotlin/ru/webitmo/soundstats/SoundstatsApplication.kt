package ru.webitmo.soundstats

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SoundstatsApplication

fun main(args: Array<String>) {
	runApplication<SoundstatsApplication>(*args)
}
