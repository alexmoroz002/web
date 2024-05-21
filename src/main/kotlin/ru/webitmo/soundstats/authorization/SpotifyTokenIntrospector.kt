package ru.webitmo.soundstats.authorization

import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.introspection.BadOpaqueTokenException
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import reactor.core.publisher.Mono
import ru.webitmo.soundstats.authorization.dto.SpotifyUserDto

class SpotifyTokenIntrospector : OpaqueTokenIntrospector {
    override fun introspect(token: String?) : OAuth2AuthenticatedPrincipal {
        val result = WebClient.create().get().uri("https://api.spotify.com/v1/me")
            .header("Authorization", "Bearer $token")
            .retrieve()
            .onStatus({ code -> code.isError }, {
                throw BadOpaqueTokenException("Provided token isn't active")
            })
            .bodyToMono(SpotifyUserDto::class.java)
            .block()
        if (result == null)
            throw BadOpaqueTokenException("Error on token exchange")
        val attributes = mapOf(Pair("active", true), Pair("client_id", result.id), Pair("username", result.displayName))
        return OAuth2IntrospectionAuthenticatedPrincipal(result.id, attributes, emptyList())
    }
}