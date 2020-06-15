package poc.web.reactive.handle

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import poc.service.UserService
import reactor.core.publisher.Mono

@Component
class UserHandler(
    val userService: UserService
) {
    fun findUser(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id").toLong()
        val user = userService.findUser(id)
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(user))
    }
}
