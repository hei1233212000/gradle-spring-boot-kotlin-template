package poc.service

import org.slf4j.Logger
import org.springframework.stereotype.Service
import poc.config.CustomProperties
import poc.model.User

@Service
class UserService(
    val customProperties: CustomProperties,
    val logger: Logger
) {
    fun findUser(userId: Long): User {
        logger.info("userId: {}", userId)
        return User(
            id = userId,
            name = "Peter",
            greetingMessage = customProperties.message
        )
    }
}