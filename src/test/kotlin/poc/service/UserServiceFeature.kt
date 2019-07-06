package poc.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be`
import org.slf4j.Logger
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import poc.config.CustomProperties
import poc.model.User
import com.nhaarman.mockitokotlin2.check as argCheck

object UserServiceFeature : Spek({
    Feature("Find user feature") {
        Scenario("Find user by ID") {
            val userId = 1L
            val message = "This is a testing message"

            lateinit var logger: Logger
            lateinit var customProperties: CustomProperties
            lateinit var userService: UserService
            lateinit var result: User

            Given("the greeting message is defined") {
                customProperties = CustomProperties().apply {
                    this.message = message
                }
            }

            And("logger is constructed") {
                logger = mock()
            }

            And("userService is prepared") {
                userService = UserService(customProperties, logger)
            }

            When("retrieve the greeting from the user service") {
                result = userService.findUser(userId)
            }

            Then("user result should have ID = $userId") {
                result.id `should be equal to` userId
            }

            And("user result should have name defined") {
                result.name `should be equal to` "Peter"
            }

            And("user result should have greeting message defined") {
                result.greetingMessage `should be equal to` message
            }

            And("the operation should have a log message") {
                verify(logger).info(
                    argCheck {
                        it `should be equal to` "userId: {}"
                    },
                    argCheck<Any> {
                        it `should be` userId
                    }
                )
            }
        }
    }
})
