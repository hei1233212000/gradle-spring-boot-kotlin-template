package poc.test

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureStubRunner(
    ids = ["poc:gradle-spring-boot-kotlin-template:+:stubs:8100"]
)
abstract class AbstractAcceptanceTest
