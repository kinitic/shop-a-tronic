Feature: Tests the retrieval of offers and the addition of offers for specific currencies

  Scenario: Navigating to the GBP offers by clicking on the GBP link in the Currency Api's response
    Given I invoke the Currencies Api endpoint
    When I click the 'GBP' offers link
    Then the response is 200
    And I should see the following offer entries for 'GBP':
      | id | name           | category    | startDate  | endDate    | price | link                                                     |
      | 1  | Cuddly Toy     | Toys        | 2017-12-10 | 2020-12-10 | 10.99 | http://localhost:8080/kinitic-shop/currencies/1/offers/1 |
      | 2  | Toaster        | Electricals | 2018-01-26 | 2020-01-26 | 12.95 | http://localhost:8080/kinitic-shop/currencies/1/offers/2 |
      | 3  | Effective Java | Books       | 2016-01-10 | 2020-12-22 | 29.99 | http://localhost:8080/kinitic-shop/currencies/1/offers/3 |
    When I click 'Cuddly Toy' offer link
    Then the response is 200
    And I should see the following offer:
      | id | name           | category    | startDate  | endDate    | price | link                                                     |
      | 1  | Cuddly Toy     | Toys        | 2017-12-10 | 2020-12-10 | 10.99 | http://localhost:8080/kinitic-shop/currencies/1/offers/1 |

