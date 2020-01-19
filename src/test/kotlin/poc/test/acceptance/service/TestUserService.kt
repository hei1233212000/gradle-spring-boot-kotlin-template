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
    fun findUserById(userId: Long, useProxyApi: Boolean = false): User? {
        val path = if (useProxyApi) "users-proxy" else "users"
        return restTemplate.getForEntity("http://localhost:$port/$path/$userId", User::class.java).body
    }
}