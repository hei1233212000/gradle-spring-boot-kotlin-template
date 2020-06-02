package poc.test.acceptance

import io.cucumber.java8.En
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.test.context.ActiveProfiles

/**
 * It needs to be a Glue to make cucumber could scan it out. That's why it implements the [En]
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureStubRunner(
    ids = ["poc:gradle-spring-boot-kotlin-template:+:stubs:8100"]
)
@Suppress("unused")
class StartupSpringInCucumber : En
