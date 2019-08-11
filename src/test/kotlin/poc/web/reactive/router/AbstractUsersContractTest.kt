package poc.web.reactive.router

import com.nhaarman.mockitokotlin2.given
import io.restassured.RestAssured
import io.restassured.module.webtestclient.RestAssuredWebTestClient
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import poc.model.User
import poc.service.UserService
import poc.web.reactive.handle.UserHandler

abstract class AbstractUsersContractTest {
    @Mock
    private lateinit var userService: UserService

    @BeforeEach
    fun beforeEach() {
        MockitoAnnotations.initMocks(this)
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

        val user = User(
            id = 1L,
            name = "David",
            greetingMessage = "Nice to meet you!"
        )
        given(userService.findUser(user.id))
            .willReturn(user)

        val userHandler = UserHandler(userService)
        val userRouter = UserRouter()
        RestAssuredWebTestClient.standaloneSetup(userRouter.route(userHandler))
    }
}
