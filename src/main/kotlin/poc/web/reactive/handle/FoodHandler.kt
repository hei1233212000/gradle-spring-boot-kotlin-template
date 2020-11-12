package poc.web.reactive.handle

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import poc.model.Food
import poc.service.FoodService
import reactor.core.publisher.Mono

@Component
class FoodHandler(
    private val foodService: FoodService
) {
    fun findFood(request: ServerRequest): Mono<ServerResponse> {
        val foodName = request.pathVariable("foodName")
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(foodService.findFood(foodName), Food::class.java)
    }
}
