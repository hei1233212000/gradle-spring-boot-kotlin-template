package poc.service

import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import poc.config.CustomProperties
import poc.model.User

object UserServiceFeature : Spek({
    Feature("Find user feature") {
        Scenario("Find user by ID") {
            val userId = 1L
            val message = "This is a testing message"
            lateinit var customProperties: CustomProperties
            lateinit var userService: UserService
            lateinit var result: User

            Given("the greeting message is defined") {
                customProperties = CustomProperties().apply {
                    this.message = message
                }
            }

            Given("userService is prepared") {
                userService = UserService(customProperties)
            }

            When("retrieve the greeting from the Greeting handler") {
                result = userService.findUser(userId)
            }

            Then("user result should have ID = $userId") {
                result.id `should be equal to` userId
            }

            Then("user result should have name defined") {
                result.name `should be equal to` "Peter"
            }

            Then("user result should have greeting message defined") {
                result.greetingMessage `should be equal to` message
            }
        }
    }
})
