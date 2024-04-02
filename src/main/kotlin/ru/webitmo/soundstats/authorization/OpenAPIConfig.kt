package ru.webitmo.soundstats.authorization

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.OAuthFlow
import io.swagger.v3.oas.annotations.security.OAuthFlows
import io.swagger.v3.oas.annotations.security.OAuthScope
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(title = "Application REST API", description = "My application REST API", version = "0.0.1"),
    servers = [Server(url = "http://localhost:1984", description = "Default URL")]
)
@SecurityScheme(name = "authorization", type = SecuritySchemeType.OAUTH2,
    flows = OAuthFlows(
        authorizationCode = OAuthFlow(
            authorizationUrl = "https://accounts.spotify.com/authorize",
            tokenUrl = "https://accounts.spotify.com/api/token",
            scopes = [
                OAuthScope(name = "user-read-private", description = "Read subscription details"),
                OAuthScope(name = "user-top-read", description = "Read user top items"),
                OAuthScope(name = "user-follow-read", description = "Read user followed items")
            ]
        )
    )
)
class OpenAPIConfig