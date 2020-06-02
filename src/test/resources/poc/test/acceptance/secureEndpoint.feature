Feature: Some of the endpoints is secured by OAuth2 and JWT

  Scenario Outline: Try to access protected endpoints with different scopes
    Given I am a <userScope> user
    When I try to access the protected endpoint for "<protectedEndpoint>"
    Then I should receive status code <expectedStatusCode>
    Then I should receive "<expectedResponseBody>"
    Examples:
      | userScope | protectedEndpoint | expectedStatusCode | expectedResponseBody    |
      | anonymous | anyone            | 401                |                         |
      | normal    | anyone            | 200                | You are \"any subject\" |
      | reader    | anyone            | 200                | You are \"any subject\" |
      | writer    | anyone            | 200                | You are \"any subject\" |
      | admin     | anyone            | 200                | You are \"any subject\" |
      | anonymous | reader            | 401                |                         |
      | normal    | reader            | 403                |                         |
      | reader    | reader            | 200                | You have READ access    |
      | writer    | reader            | 403                |                         |
      | admin     | reader            | 200                | You have READ access    |
      | anonymous | writer            | 401                |                         |
      | normal    | writer            | 403                |                         |
      | reader    | writer            | 403                |                         |
      | writer    | writer            | 200                | You have WRITE access   |
      | admin     | writer            | 200                | You have WRITE access   |