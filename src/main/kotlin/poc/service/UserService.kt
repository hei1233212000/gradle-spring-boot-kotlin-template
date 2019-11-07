package poc.service

import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
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

    fun findUserByRestfulCall(userId: Long): User? {
        logger.info("userId: {}", userId)
        return RestTemplate().getForObject("${customProperties.userProxyUrl}/$userId", User::class.java)
    }
}
