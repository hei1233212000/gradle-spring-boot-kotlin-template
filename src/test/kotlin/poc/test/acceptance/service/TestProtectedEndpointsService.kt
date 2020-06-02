package poc.test.acceptance.service

import io.cucumber.spring.CucumberTestContext
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.function.Consumer

@Service
@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
class TestProtectedEndpointsService(
    @LocalServerPort
    private val port: Int
) {
    private val webClient = WebTestClient
        .bindToServer()
        .baseUrl("http://localhost:$port/protected")
        .build()

    fun findProtectedResource(jwt: String?, path: String): ProtectedResourceResponse {
        val headers = Consumer { http: HttpHeaders -> jwt?.let { http.setBearerAuth(jwt) } }

        val response = webClient.get()
            .uri(path)
            .headers(headers)
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .returnResult(String::class.java)

        return ProtectedResourceResponse(
            status = response.rawStatusCode,
            body = response.responseBodyContent?.toString(Charsets.UTF_8) ?: ""
        )
    }

    data class ProtectedResourceResponse(
        val status: Int,
        val body: String
    )
}