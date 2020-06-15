package poc.test.acceptance.service

import io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import poc.model.User

@Service
@Scope(SCOPE_CUCUMBER_GLUE)
class TestUserService(
    private val restTemplate: TestRestTemplate,
    @LocalServerPort
    private val port: Int
) {
    fun findUserById(userId: Long): User? {
        return restTemplate.getForEntity("http://localhost:$port/users/$userId", User::class.java).body
    }
}