package poc.service

import org.springframework.stereotype.Service
import poc.model.Food
import poc.model.FoodEntity
import poc.repository.FoodRepository
import reactor.core.publisher.Mono

@Service
class FoodService(
    private val foodRepository: FoodRepository
) {
    fun findFood(foodName: String): Mono<Food> {
        return foodRepository.findByName(foodName)
            .map {
                it.toFood()
            }
    }

    fun FoodEntity.toFood(): Food {
        return Food(
            id = this.id!!,
            name = this.name
        )
    }
}