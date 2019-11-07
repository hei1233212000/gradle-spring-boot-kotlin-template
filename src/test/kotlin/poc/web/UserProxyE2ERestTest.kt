package poc.web

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.core.Is
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.test.context.ActiveProfiles

/**
 * This End-to-End test is using the Stub of external service
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(
    ids = ["poc:gradle-spring-boot-kotlin-template:+:stubs:8100"]
)
@ActiveProfiles("test")
class UserProxyE2ERestTest {
    @LocalServerPort
    private lateinit var port: String

    @BeforeEach
    fun beforeEach() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Test
    fun `should return user when called the user proxy API`() {
        // Given
        val userId = 1

        given()
            .accept(ContentType.JSON)
        .`when`()
            .get("http://localhost:$port/users-proxy/$userId")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            // the response will be provided by the stub
            .body("id", Is.`is`(userId))
            .body("name", IsEqual.equalTo("David"))
            .body("greetingMessage", IsEqual.equalTo("Nice to meet you!"))
    }
}