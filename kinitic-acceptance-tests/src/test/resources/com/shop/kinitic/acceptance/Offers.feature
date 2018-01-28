Feature: Tests the retrieval of offers and the addition of offers for specific currencies

  Scenario: Navigating to the GBP offers by clicking on the GBP link in the Currency Api's response
    Given I invoke the Currencies Api endpoint
    When I click the 'GBP' offers link
    Then the response is 200
    And I should see the following 'active' offer entries for 'GBP':
      | id | name           | category    | startDate  | expiryDate    | price | link                                                     |
      | 1  | Cuddly Toy     | Toys        | 2017-12-10 | 2020-12-10 | 10.99 | http://localhost:8080/kinitic-shop/currencies/1/offers/1 |
      | 2  | Toaster        | Electricals | 2018-01-26 | 2020-01-26 | 12.95 | http://localhost:8080/kinitic-shop/currencies/1/offers/2 |
      | 3  | Effective Java | Books       | 2016-01-10 | 2020-12-22 | 29.99 | http://localhost:8080/kinitic-shop/currencies/1/offers/3 |
    When I click the active 'Cuddly Toy' offer link
    Then the response is 200
    And I should see the following offer:
      | id | name       | category | startDate  | expiryDate    | price | link                                                     |
      | 1  | Cuddly Toy | Toys     | 2017-12-10 | 2020-12-10 | 10.99 | http://localhost:8080/kinitic-shop/currencies/1/offers/1 |


#    this test is work in progress, the step defs assertions need to be finessed.
  Scenario: After adding new active offers for US Dollars, the offer can be seen in the response for retrieving US Dollars offers
    Given I add an offer in the kinitic shop for the 'USD' currency with the following details:
      | name                         | category    | startDate  | expiryDate | price  |
      | Fridge Freezer               | Electricals | 2018-01-01 | 2018-12-31 | 149.99 |
      | Game of Thrones DVD Season 6 | DVDs        | 2017-01-01 | 2020-01-01 | 10.99  |
    When I invoke the Currencies Api endpoint
    And I click the 'USD' offers link
    Then the response is 200
    And I should see the following 'active' offer entries for 'USD':
      | id | name                         | category    | startDate  | expiryDate    | price  | link                                                     |
      | 1  | Fridge Freezer               | Electricals | 2018-01-01 | 2018-12-31 | 149.99 | http://localhost:8080/kinitic-shop/currencies/1/offers/1 |
      | 2  | Game of Thrones DVD Season 6 | Ds          | 2017-01-01 | 2020-01-01 | 10.99  | http://localhost:8080/kinitic-shop/currencies/1/offers/2 |

    
#    this test is work in progress too ;-(
  Scenario: After setting an offer to expire, the offer can be seen to have moved from active list to the expired list
    