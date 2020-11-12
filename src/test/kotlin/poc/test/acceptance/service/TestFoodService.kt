package poc.test.acceptance.service

import io.cucumber.spring.CucumberTestContext
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Scope
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.stereotype.Service
import poc.model.Food
import poc.model.FoodEntity

@Service
@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
class TestFoodService(
    private val restTemplate: TestRestTemplate,
    @LocalServerPort
    private val port: Int,
    private val r2dbcEntityTemplate: R2dbcEntityTemplate
) {
    fun isFoodExist(foodName: String): Boolean {
        return r2dbcEntityTemplate.select(FoodEntity::class.java)
            .from("FOOD")
            .matching(query(where("name").`is`(foodName)))
            .all()
            .count()
            .block()!! > 0
    }

    fun findFood(foodName: String): Food? {
        return restTemplate.getForEntity("http://localhost:$port/foods/$foodName", Food::class.java).body
    }
}
