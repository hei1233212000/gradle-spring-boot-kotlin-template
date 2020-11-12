Feature: Find Food

  Scenario: Find food by name
    Given food with name "Apple" already exist
    When I try to find food "Apple"
    Then the name of the retrieved food is "Apple"
    And the id of the retrieved food is not null

  Scenario: Food not found case
    Given food with name "Fake" does not exist
    When I try to find food "Fake"
    Then No food is found
