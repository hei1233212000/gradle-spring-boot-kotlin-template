package poc.service

import org.springframework.stereotype.Service
import poc.config.CustomProperties
import poc.model.User

@Service
class UserService(
    val customProperties: CustomProperties
) {
    fun findUser(userId: Long) = User(
        id = userId,
        name = "Peter",
        greetingMessage = customProperties.message
    )
}
