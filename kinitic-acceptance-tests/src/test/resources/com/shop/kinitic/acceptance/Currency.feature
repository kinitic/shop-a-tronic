Feature: Tests covering the Currency API.

  Scenario: Should provide all the available currencies for the kinitic shop
    Given I invoke the currencies Api endpoint
    Then the response is 200
