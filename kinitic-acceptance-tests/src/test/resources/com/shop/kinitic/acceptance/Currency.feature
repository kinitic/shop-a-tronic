Feature: Tests covering the Currency API.

  Scenario: Should provide all the available currencies for the kinitic shop. ({id} field in data table will be populated in the step definition's assertion)
    Given I invoke the Currencies Api endpoint
    Then the response is 200
    And the response contains the following Currency entries:
      | id | name | description     | link                                                 |
      | 1  | GBP  | Pounds Sterling | http://localhost:8080/kinitic-shop/currency/1/offers |
      | 2  | USD  | US Dollars      | http://localhost:8080/kinitic-shop/currency/2/offers |


