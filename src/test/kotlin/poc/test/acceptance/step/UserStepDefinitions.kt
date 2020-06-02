package poc.test.acceptance.step

import io.cucumber.java8.En
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be`
import poc.model.User
import poc.test.acceptance.service.TestUserService

@Suppress("unused")
class UserStepDefinitions(
    private val testUserService: TestUserService
) : En {
    private var user: User? = null

    init {
        Given("user with id {long} already exist") { _: Long -> /* do nothing for the moment */ }

        When("I try to find user by id = {long}") { userId: Long ->
            user = null
            user = testUserService.findUserById(userId)
        }

        When("I try to find user by id = {long} with proxy API") { userId: Long ->
            user = null
            user = testUserService.findUserById(userId, useProxyApi = true)
        }

        Then("the name of the retrieved user is {string}") { userName: String ->
            user `should not be` null
            user!!.name `should be equal to` userName
        }

        Then("the greeting message of the retrieved user is {string}") { greetingMessage: String ->
            user `should not be` null
            user!!.greetingMessage `should be equal to` greetingMessage
        }
    }
}