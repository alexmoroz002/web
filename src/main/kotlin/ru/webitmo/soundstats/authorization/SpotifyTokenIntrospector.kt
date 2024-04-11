package ru.webitmo.soundstats.authorization

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.introspection.BadOpaqueTokenException
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class SpotifyTokenIntrospector : OpaqueTokenIntrospector {
    override fun introspect(token: String?): OAuth2AuthenticatedPrincipal {
        val result = WebClient.create().get().uri("https://api.spotify.com/v1/me")
            .header("Authorization", "Bearer $token").exchangeToMono {
            response -> Mono.just(response.statusCode())
        }.block()
        if (result == null || !result.is2xxSuccessful) {
            throw BadOpaqueTokenException("Provided token isn't active")
        }
        val attributes = mapOf(Pair<String, Any>("active", true))
        return OAuth2IntrospectionAuthenticatedPrincipal(attributes, emptyList())
    }
}