package poc.repository

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import poc.config.DatabaseConfiguration
import reactor.core.publisher.Hooks
import reactor.test.StepVerifier
import java.time.Duration

@DataR2dbcTest
@Import(DatabaseConfiguration::class)
class FoodRepositoryTest {
    @Autowired
    private lateinit var foodRepository: FoodRepository

    @BeforeEach
    fun setUp() {
        Hooks.onOperatorDebug()
    }

    @Test
    fun `should able find Food by name`() {
        // given
        val name = "Apple"

        // when
        val foodMono = foodRepository.findByName(name)

        // then
        foodMono
            .take(Duration.ofSeconds(1))
            .log()
            .`as`(StepVerifier::create)
            .consumeNextWith {
                it.id `should not be` null
                it.name `should be equal to` name
            }
            .verifyComplete()
    }
}