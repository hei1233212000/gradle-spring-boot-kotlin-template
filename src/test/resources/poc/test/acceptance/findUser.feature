Feature: Find User

  Scenario: Find user by id
    Given user with id 1 already exist
    When I try to find user by id = 1
    Then the name of the retrieved user is "Peter"
    And the greeting message of the retrieved user is "This is a test message"
