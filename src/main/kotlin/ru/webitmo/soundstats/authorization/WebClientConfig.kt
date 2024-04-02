package ru.webitmo.soundstats.authorization

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration


@Configuration
@EnableWebSecurity
@Order(1)
@Suppress("SpringJavaInjectionPointsAutowiringInspection")
class WebClientConfig(val customUserService : UsersAuthService) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeRequests {
                authorize("/", permitAll)
                authorize("/v3/api-docs/**", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize("/swagger-ui.html", permitAll)
                authorize("/css/**", permitAll)
                authorize("/js/**", permitAll)
                authorize("/common.blocks/**", permitAll)
                authorize("/images/**", permitAll)
                authorize("/api/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2Login {
                userInfoEndpoint { userService = oauth2UserService() }
            }
            oauth2Client {  }
        }
        return http.build()
    }


    @Bean
    fun oauth2UserService() : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
        return OAuth2UserService { userRequest ->
            val oAuth2User = customUserService.loadUser(userRequest)
            oAuth2User
        }
    }

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
    fun oAuth2AuthorizedClientService(jdbcOperations: JdbcOperations?, clientRegistrationRepository: ClientRegistrationRepository?): OAuth2AuthorizedClientService {
        return JdbcOAuth2AuthorizedClientService(jdbcOperations, clientRegistrationRepository)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}