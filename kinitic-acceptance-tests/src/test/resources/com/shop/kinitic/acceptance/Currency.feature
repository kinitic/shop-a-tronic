Feature: Tests covering the Currency API. (the currency data is supplied when the application is bootstrapped on start-up)

  Scenario: Should provide all the available currencies for the kinitic shop.
    Given I invoke the Currencies Api endpoint
    Then the response is 200
    And the response contains the following Currency entries:
      | id | name | description     | link                                                 |
      | 1  | GBP  | Pounds Sterling | http://localhost:8080/kinitic-shop/currencies/1/offers |
      | 2  | USD  | US Dollars      | http://localhost:8080/kinitic-shop/currencies/2/offers |


