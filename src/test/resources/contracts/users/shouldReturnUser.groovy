package users

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Should return user with id = 1'
    request {
        url '/users/1'
        method GET()
        headers {
            accept applicationJson()
        }
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body([
                id: 1,
                name: "David",
                greetingMessage: "Nice to meet you!"
        ])
    }
}
