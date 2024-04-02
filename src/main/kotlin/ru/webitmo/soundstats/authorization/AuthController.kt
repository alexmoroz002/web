package ru.webitmo.soundstats.authorization

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
@SecurityRequirement(name = "auth")
class AuthController(private val webClient: WebClient) {
    @Operation(summary = "Department Users",  description = "Retrieves the Users of a Department by DeparmentID",
        security = [SecurityRequirement(name = "auth")]
    )
    @GetMapping("/me")
    fun userInfo() : Mono<String> {
        return webClient.get()
            .uri("https://api.spotify.com/v1/me")
            .retrieve()
            .bodyToMono(String::class.java)
    }

    @Operation(summary = "Department Users",  description = "Retrieves the Users of a Department by DeparmentID",
        security = [SecurityRequirement(name = "auth")]
    )
    @GetMapping("/artists")
    fun artists() : Mono<String> {
        return webClient.get()
            .uri("https://api.spotify.com/v1/me/top/artists")
            .retrieve()
            .bodyToMono(String::class.java)
    }

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientException(ex : WebClientResponseException) : ResponseEntity<String> =
        ResponseEntity<String>(ex.responseBodyAsString, ex.headers, ex.statusCode)
}