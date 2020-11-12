package poc.test.acceptance.step

import io.cucumber.java8.En
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should not be`
import poc.model.Food
import poc.test.acceptance.service.TestFoodService

@Suppress("unused")
class FoodStepDefinitions(
    private val testFoodService: TestFoodService
) : En {
    private var food: Food? = null

    init {
        Given("food with name {string} already exist") { foodName: String ->
            testFoodService.isFoodExist(foodName) `should be` true
        }

        Given("food with name {string} does not exist") { foodName: String ->
            testFoodService.isFoodExist(foodName) `should be` false
        }

        When("I try to find food {string}") { foodName: String ->
            food = testFoodService.findFood(foodName)
        }

        Then("the name of the retrieved food is {string}") { foodName: String ->
            food?.name `should be equal to` foodName
        }

        Then("the id of the retrieved food is not null") {
            food?.id `should not be` null
        }

        Then("No food is found") {
            food `should be` null
        }
    }
}