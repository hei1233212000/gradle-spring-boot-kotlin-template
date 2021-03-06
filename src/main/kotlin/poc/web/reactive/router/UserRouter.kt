package poc.web.reactive.router

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import poc.web.reactive.handle.UserHandler

@Configuration
class UserRouter {
    @Bean
    fun getAllUserRoutes(userHandler: UserHandler): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(
                RequestPredicates.GET("/users/{id}")
                    .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                HandlerFunction { userHandler.findUser(it) }
            )
    }
}
