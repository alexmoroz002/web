package ru.webitmo.soundstats.spotify

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration


@Configuration
class CustomWebClientConfig {
    @Bean
    fun webClient(authorizedClientManager: OAuth2AuthorizedClientManager): WebClient {
        val filter = ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager)
        filter.setDefaultOAuth2AuthorizedClient(true)
        val provider = ConnectionProvider.builder("fixed")
            .maxConnections(500)
            .maxIdleTime(Duration.ofSeconds(20))
            .maxLifeTime(Duration.ofSeconds(60))
            .pendingAcquireTimeout(Duration.ofSeconds(60))
            .evictInBackground(Duration.ofSeconds(120)).build()

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(HttpClient.create(provider)))
            .filter(ServletBearerExchangeFilterFunction())
            .apply(filter.oauth2Configuration())
            .build()
    }

    @Bean
    fun authorizedClientManager(
        clientRegistrationRepository: ClientRegistrationRepository?,
        clientService: OAuth2AuthorizedClientService?
    ): OAuth2AuthorizedClientManager {
        val authorizedClientProvider =
            OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build()

        val authorizedClientManager =
            AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository, clientService
            )
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)

        return authorizedClientManager
    }
}