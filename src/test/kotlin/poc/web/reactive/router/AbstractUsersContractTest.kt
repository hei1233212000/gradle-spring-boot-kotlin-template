package poc.web.reactive.router

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.restassured.RestAssured
import io.restassured.module.webtestclient.RestAssuredWebTestClient
import org.junit.jupiter.api.BeforeEach
import poc.model.User
import poc.service.UserService
import poc.web.reactive.handle.UserHandler

abstract class AbstractUsersContractTest {
    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this)
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

        val user = User(
            id = 1L,
            name = "David",
            greetingMessage = "Nice to meet you!"
        )
        val userService = mockk<UserService> {
            every { findUser(user.id) } returns user
        }

        val userHandler = UserHandler(userService)
        val userRouter = UserRouter()
        RestAssuredWebTestClient.standaloneSetup(userRouter.getAllUserRoutes(userHandler))
    }
}
