package poc.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import poc.model.FoodEntity
import reactor.core.publisher.Mono

interface FoodRepository : ReactiveCrudRepository<FoodEntity, Long> {
    @Query("SELECT * FROM FOOD WHERE name = :name")
    fun findByName(name: String): Mono<FoodEntity>
}
