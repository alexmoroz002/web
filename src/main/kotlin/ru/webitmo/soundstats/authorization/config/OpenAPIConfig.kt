package ru.webitmo.soundstats.authorization.config

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
    info = Info(title = "Application REST API", description = "My application REST API", version = "0.0.1")
)
@SecurityScheme(name = "authorization", type = SecuritySchemeType.OAUTH2,
    flows = OAuthFlows(
        authorizationCode = OAuthFlow(
            authorizationUrl = "https://accounts.spotify.com/authorize",
            tokenUrl = "https://accounts.spotify.com/api/token",
            scopes = [
                OAuthScope(name = "user-read-private", description = "Read subscription details"),
                OAuthScope(name = "user-top-read", description = "Read user top items"),
                OAuthScope(name = "user-follow-read", description = "Read user followed items"),
                OAuthScope(name = "playlist-modify-private", description = "Edit private playlists"),
                OAuthScope(name = "playlist-modify-public", description = "Edit public playlists"),
                OAuthScope(name = "user-library-read", description = "Read user saved tracks"),
                OAuthScope(name = "user-library-modify", description = "Modify user saved tracks")
            ]
        )
    )
)
class OpenAPIConfig