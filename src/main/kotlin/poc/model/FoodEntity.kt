package poc.model

import org.springframework.data.annotation.Id

data class FoodEntity(
    @Id
    val id: Long? = null,
    val name: String
)