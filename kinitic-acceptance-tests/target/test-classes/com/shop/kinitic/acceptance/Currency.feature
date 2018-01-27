Feature: Tests covering the Currency API.

  Scenario: Should provide all the available currencies for the kinitic shop
    Given I invoke the currencies Api endpoint
    Then the response is 200
    And the response contains the following currency entries:
      | name | description     |
      | USD  | US Dollars      |
      | GBP  | Pounds Sterling |
    And the id exists

