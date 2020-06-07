package poc.web.reactive.router

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status

@Configuration
class ProtectedResourcesRouter {
    @Bean
    fun routeTpProtectedResources(): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(
                GET("/protected/reader"),
                HandlerFunction {
                    ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(fromValue("You have READ access"))
                }
            )
            .andRoute(
                GET("/protected/writer"),
                HandlerFunction {
                    ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(fromValue("You have WRITE access"))
                }
            )
            .andRoute(
                GET("/protected"),
                HandlerFunction { serverRequest ->
                    serverRequest.principal()
                        .map { it as JwtAuthenticationToken }
                        .flatMap { jwtAuthenticationToken ->
                            ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .body(fromValue("You are \"${jwtAuthenticationToken.name}\""))
                        }.switchIfEmpty(
                            status(HttpStatus.FORBIDDEN).body(fromValue("You have to provide JWT"))
                        )
                }
            )
    }
}