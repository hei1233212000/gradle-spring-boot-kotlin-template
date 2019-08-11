package poc.web

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.core.Is
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserE2ERestTest {
    @LocalServerPort
    private lateinit var port: String

    @BeforeEach
    fun beforeEach() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Test
    fun `should return user`() {
        // Given
        val userId = 1

        RestAssured.given()
            .accept(ContentType.JSON)
        .`when`()
            .get("http://localhost:$port/users/$userId")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", Is.`is`(userId))
            .body("name", IsEqual.equalTo("Peter"))
            .body("greetingMessage", IsEqual.equalTo("This is a test message"))
    }
}