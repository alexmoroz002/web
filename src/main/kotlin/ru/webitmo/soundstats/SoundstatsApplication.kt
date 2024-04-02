package ru.webitmo.soundstats

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.OAuthFlow
import io.swagger.v3.oas.annotations.security.OAuthFlows
import io.swagger.v3.oas.annotations.security.OAuthScope
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@SecurityScheme(name = "auth", type = SecuritySchemeType.OAUTH2,
	flows = OAuthFlows(
		authorizationCode = OAuthFlow(
			authorizationUrl = "https://accounts.spotify.com/authorize",
			tokenUrl = "https://accounts.spotify.com/api/token",
			scopes = [
				OAuthScope(name = "user-read-private", description = "Read user info"),
				OAuthScope(name = "user-top-read", description = "Read user top items")
			]
		)
	)
)
class SoundstatsApplication

fun main(args: Array<String>) {
	runApplication<SoundstatsApplication>(*args)
}
