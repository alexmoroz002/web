package ru.webitmo.soundstats.authorization

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@Order(2)
class ResourceServerConfig {
    @Bean
    fun resourceFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            securityMatcher("/api/**")
            authorizeRequests {
                authorize("/api/**", authenticated)
                authorize(anyRequest, permitAll)
            }
            oauth2ResourceServer {
                opaqueToken {
                    introspector = SpotifyTokenIntrospector()
                }
            }
        }
        return http.build()
    }
}