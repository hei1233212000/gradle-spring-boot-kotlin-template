package poc.web.reactive.router

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*

@Configuration
class ProtectedResourcesRouter {
    @Bean
    fun routeTpProtectedResources(): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(
                RequestPredicates.GET("/protected/reader"),
                HandlerFunction {
                    ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(BodyInserters.fromValue("You have READ access"))
                }
            )
            .andRoute(
                RequestPredicates.GET("/protected/writer"),
                HandlerFunction {
                    ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(BodyInserters.fromValue("You have WRITE access"))
                }
            )
            .andRoute(
                RequestPredicates.GET("/protected"),
                HandlerFunction { serverRequest ->
                    serverRequest.principal()
                        .map { it as JwtAuthenticationToken }
                        .flatMap { jwtAuthenticationToken ->
                            ServerResponse.ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .body(BodyInserters.fromValue("You are \"${jwtAuthenticationToken.name}\""))
                        }
                }
            )
    }
}