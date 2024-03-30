package ru.webitmo.soundstats.authorization

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@RestController
class AuthController(private val webClient: WebClient) {
    @RequestMapping("/sso")
    fun user(@RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient) : Mono<String> {
        return webClient.get()
            .uri("https://api.spotify.com/v1/me")
            .attributes(oauth2AuthorizedClient(authorizedClient))
            .retrieve()
            .bodyToMono(String::class.java)
    }

    @RequestMapping("/test")
    fun test() : String {
        val user = SecurityContextHolder.getContext().authentication
        return user.name
    }
}