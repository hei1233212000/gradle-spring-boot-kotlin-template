package poc.contract.rest

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.core.Is
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.test.context.ActiveProfiles

/**
 * This is consumer side contract test
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
    ids = ["poc:gradle-spring-boot-kotlin-template:+:stubs:8100"]
)
@ActiveProfiles("test")
class UserEndPointContractTest {
    @BeforeEach
    fun beforeEach() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Test
    fun `should return user when called the external user API`() {
        // Given
        val userId = 1

        given()
            .accept(ContentType.JSON)
        .`when`()
            .get("http://localhost:8100/users/$userId")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            // the response will be provided by the stub
            .body("id", Is.`is`(userId))
            .body("name", IsEqual.equalTo("David"))
            .body("greetingMessage", IsEqual.equalTo("Nice to meet you!"))
    }
}