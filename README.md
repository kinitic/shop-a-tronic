# shop-a-tronic

This is a Spring boot application to add, retrieve and update offers to my pretend shop.

To run the application, go the shop-a-tronic/kinitic-shop-api directory and:

run > `./mvnw clean install` 

This will run all the unit tests and make sures that the artefacts required are downloaded.

run > `./mvnw spring-boot:run`

This will start up the application. 

The application loads 2 currencies at start-up, US Dollars and Pounds. Offers are **only** associated with the Pounds at start-up.

All the APIs implemented working across specified currencies, the APIs available:

1. `GET` http://localhost:8080/kinitic-shop/currencies

**Retrieves all currencies supported by the kinitic shop.**

Example response:

````
{
    "currencies": [
        {
            "id": 1,
            "name": "GBP",
            "description": "Pounds Sterling",
            "link": "http://localhost:8080/kinitic-shop/currencies/1/offers"
        },
        {
            "id": 2,
            "name": "USD",
            "description": "US Dollars",
            "link": "http://localhost:8080/kinitic-shop/currencies/2/offers"
        }
    ]
}
````
The response contains embedded links (HATEAOS-style) so the user can drill directly into the currency offers.

2. `GET` http://localhost:8080/kinitic-shop/currencies/{currencyId}/offers
 
**Retrieves all offers associated for the currencyId by the kinitic shop.**
 
Example response:
````
{
    "name": "GBP",
    "activeOffers": [
        {
           "id": 1,
           "name": "Cuddly Toy",
           "category": "Toys",
           "startDate": "2017-12-10",
           "expiryDate": "2020-12-10",
           "price": 10.99,
           "link": "http://localhost:8080/kinitic-shop/currencies/1/offers/1"
        }
    ],
    "expiredOffers": [
        {
           "id": 4,
           "name": "Sandwich",
           "category": "Groceries",
           "startDate": "2017-12-10",
           "expiryDate": "2017-12-31",
           "price": 1.99,
           "link": "http://localhost:8080/kinitic-shop/currencies/1/offers/4"
       }
   ]
}
````

3. `GET` http://localhost:8080/kinitic-shop/currencies/{currencyId}/offers/{offerId}
 
**Retrieves offer details for given offerId for given currencyId.**

````
{
    "id": 1,
    "name": "Cuddly Toy",
    "category": "Toys",
    "startDate": "2017-12-10",
    "endDate": "2020-12-10",
    "price": 10.99,
    "link": "http://localhost:8080/kinitic-shop/currencies/1/offers/1"
}
````

4. `POST` http://localhost:8080/kinitic-shop/currencies/{currencyId}/offers

**Add an offer for given currencyId.**

Example payload:

````
{
    "name": "Train set",
    "category": "Toys",
    "startDate": "2017-02-11",
    "expiryDate": "2017-09-22",
    "price": "40.00"
}
````

5. `PUT` http://localhost:8080/kinitic-shop/currencies/{currencyId}/offers/{offerId}

**Update an offer resource for given offerId and currencyId.**

Example payload:

````
{
    "name": "Train set",
    "category": "Toys",
    "startDate": "2017-02-11",
    "expiryDate": "2020-10-22",
    "price": "40.00"
}
````

####Cucumber tests

As well as having unit tests, I have written some cucumber tests also. Namely:

* Currency.feature
* Offers.feature

Currently only APIs 1-3 are fully covered by cucumber tests and API test 4 is semi-covered. API 5 (the update) is not covered at all.

I generally run the cucumber tests via intellij.

