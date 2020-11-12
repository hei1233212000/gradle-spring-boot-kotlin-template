package poc.web.reactive.router

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import poc.web.reactive.handle.FoodHandler

@Configuration
class FoodRouter {
    @Bean
    fun getAllFoodRoutes(foodHandler: FoodHandler): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(
                RequestPredicates.GET("/foods/{foodName}")
                    .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                { foodHandler.findFood(it) }
            )
    }
}