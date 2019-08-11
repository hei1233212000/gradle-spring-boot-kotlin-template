package poc.web.reactive.router

import com.nhaarman.mockitokotlin2.given
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import poc.model.User
import poc.service.UserService
import poc.web.reactive.handle.UserHandler

@DisplayName("API test of /users")
@WebFluxTest(value = [UserRouter::class])
@Import(UserHandler::class)
class UserApiTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @MockBean
    private lateinit var userService: UserService

    @Test
    fun `should return user`() {
        // given
        val user = User(
            id = 1L,
            name = "David",
            greetingMessage = "Nice to meet you!"
        )

        given(userService.findUser(user.id))
            .willReturn(user)

        // then
        this.webClient.get().uri("/users/${user.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("id").isEqualTo(user.id)
            .jsonPath("name").isEqualTo(user.name)
            .jsonPath("greetingMessage").isEqualTo(user.greetingMessage)
    }
}