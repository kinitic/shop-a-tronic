Feature: Tests the retrieval of offers and the addition of offers for specific currencies

  Scenario: Navigating to the GBP offers by clicking on the GBP link in the Currency Api's response
    Given I invoke the Currencies Api endpoint
    When I click the 'GBP' offers link
    Then the response is 200
    And I should see the following offer entries for 'GBP':
      | name           | category    | startDate  | endDate    | price |
      | Cuddly Toy     | Toys        | 2017-12-10 | 2020-12-10 | 10.99 |
      | Toaster        | Electricals | 2018-01-26 | 2020-01-26 | 12.95 |
      | Effective Java | Books       | 2016-01-10 | 2020-12-22 | 29.99 |