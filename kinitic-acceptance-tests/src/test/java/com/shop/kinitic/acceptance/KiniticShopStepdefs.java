package com.shop.kinitic.acceptance;

import static com.jayway.jsonassert.JsonAssert.with;
import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;
import static com.jayway.jsonpath.JsonPath.compile;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.withJsonPath;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.client.Entity.json;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Map;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KiniticShopStepdefs {

    private static final Logger LOGGER = LoggerFactory.getLogger(KiniticShopStepdefs.class);

    private static final WebTarget KINITIC_SHOP_API_BASE_URI = newClient().target("http://localhost:8080/kinitic-shop");

    private int responseStatus;
    private String responseBody;

    @Given("^I invoke the Currencies Api endpoint$")
    public void iInvokeTheCurrenciesApiEndpoint() throws Throwable {
        final Response response = KINITIC_SHOP_API_BASE_URI.path("/currencies").request().get();
        readResponse(response);
    }

    @And("^the response is (\\d+)$")
    public void responseStatusIs(final int status) {
        assertThat(status, is(responseStatus));
    }

    @And("^the response contains the following Currency entries:$")
    public void theResponseContainsTheFollowingCurrencyEntries(final DataTable dataTable) throws Throwable {
        final List<Map<String, String>> expectedCurrencyDetails = dataTable.asMaps();

        final List<String> expectedCurrencyNames = getExpectedDetailsFor("name", expectedCurrencyDetails); // returns GBP, USD in list

        expectedCurrencyNames.forEach(expectedCurrencyName -> {
                    final Map<String, String> expectedCurrencyDetailsMap = expectedCurrencyDetails.stream().filter(currencyDetail -> currencyDetail.get("name").equals(expectedCurrencyName)).findFirst().get();

                    LOGGER.info("**** Asserting on currency: {}", expectedCurrencyName);
                    Filter currencyFilter = filter(where("name").is(expectedCurrencyName)); // filter on the specific currency name (USD v GBP)

                    assertThat(responseBody, isJson(allOf(
                            withJsonPath(compile("$.currencies[?]", currencyFilter), hasSize(1)),
                            withJsonPath(compile("$.currencies[?].id", currencyFilter), hasItem(parseInt(expectedCurrencyDetailsMap.get("id")))),
                            withJsonPath(compile("$.currencies[?].name", currencyFilter), hasItem(expectedCurrencyDetailsMap.get("name"))),
                            withJsonPath(compile("$.currencies[?].description", currencyFilter), hasItem(expectedCurrencyDetailsMap.get("description"))),
                            withJsonPath(compile("$.currencies[?].link", currencyFilter), hasItem(expectedCurrencyDetailsMap.get("link")))
                    )));
                }
        );
    }

    @When("^I click the '(.+)' offers link$")
    public void iClickTheGBPOffersLink(final String currencyName) throws Throwable {
        final Filter currencyFilter = filter(where("name").is(currencyName));

        final JSONArray jsonArray = JsonPath.read(responseBody, "$.currencies[?].link", currencyFilter);
        final String[] linksArray = jsonArray.toArray(new String[jsonArray.size()]);     // should only be 1 link matched via the filter

        readResponse(newClient().target(linksArray[0]).request().get());
    }

    @When("^I click the (.+) '(.+)' offer link$")
    public void iClickOfferLink(final String offerStatus, final String offerName) throws Throwable {
        final Filter offerFilter = filter(where("name").is(offerName));

        final JSONArray jsonArray = JsonPath.read(responseBody, "$." + offerStatus + "Offers[?].link", offerFilter);
        final String[] linksArray = jsonArray.toArray(new String[jsonArray.size()]);     // should only be 1 link matched via the filter

        readResponse(newClient().target(linksArray[0]).request().get());
    }

    @Then("^I should see the following '(.+)' offer entries for '(.+)':$")
    public void iShouldSeeTheFollowingOfferEntries(final String offerStatus, final String currencyName, final DataTable dataTable) throws Throwable {
        with(responseBody)
                .assertThat("$.name", is(currencyName));

        final List<Map<String, String>> expectedOffers = dataTable.asMaps();

        with(responseBody)
                .assertThat("$." + offerStatus + "Offers", hasSize(expectedOffers.size()));

        final List<String> expectedOfferNames = getExpectedDetailsFor("name", expectedOffers); // returns Cuddly Toy, Toaster and Effective Java in list

        expectedOfferNames.forEach(expectedOfferName -> {
                    final Map<String, String> expectedOfferDetailsMap = expectedOffers.stream().filter(currencyDetail -> currencyDetail.get("name").equals(expectedOfferName)).findFirst().get();

                    Filter currencyFilter = filter(where("name").is(expectedOfferName)); // filter on the specific offer name (Cuddly Toy v Toaster v Effective Java)

                    LOGGER.info("**** Asserting on offer: {}", expectedOfferName);
                    System.out.println("**** Asserting on offer: " + expectedOfferName);

                    assertThat(responseBody, isJson(allOf(
                            withJsonPath(compile("$." + offerStatus + "Offers[?]", currencyFilter), hasSize(1)),
                            withJsonPath(compile("$." + offerStatus + "Offers[?].id", currencyFilter), hasItem(parseInt(expectedOfferDetailsMap.get("id")))),
                            withJsonPath(compile("$." + offerStatus + "Offers[?].name", currencyFilter), hasItem(expectedOfferDetailsMap.get("name"))),
                            withJsonPath(compile("$." + offerStatus + "Offers[?].category", currencyFilter), hasItem(expectedOfferDetailsMap.get("category"))),
                            withJsonPath(compile("$." + offerStatus + "Offers[?].startDate", currencyFilter), hasItem(expectedOfferDetailsMap.get("startDate"))),
                            withJsonPath(compile("$." + offerStatus + "Offers[?].expiryDate", currencyFilter), hasItem(expectedOfferDetailsMap.get("expiryDate"))),
                            withJsonPath(compile("$." + offerStatus + "Offers[?].price", currencyFilter), notNullValue()),  // TODO: fix this assertion.
                            withJsonPath(compile("$." + offerStatus + "Offers[?].link", currencyFilter), hasItem(expectedOfferDetailsMap.get("link")))
                    )));
                }
        );
    }

    @And("^I should see the following offer:$")
    public void iShouldSeeTheFollowingOffer(final DataTable dataTable) throws Throwable {

        final Map<String, String> expectedOffer = dataTable.asMaps().get(0); // a little cheat, since we know there is only one expected row

        assertThat(responseBody, isJson(allOf(
                withJsonPath("$.id", is(parseInt(expectedOffer.get("id")))),
                withJsonPath("$.name", is(expectedOffer.get("name"))),
                withJsonPath("$.category", is(expectedOffer.get("category"))),
                withJsonPath("$.startDate", is(expectedOffer.get("startDate"))),
                withJsonPath("$.expiryDate", is(expectedOffer.get("expiryDate"))),
                withJsonPath("$.price", notNullValue()),  // TODO: fix this assertion.
                withJsonPath("$.link", is(expectedOffer.get("link"))))
        ));
    }

    private void readResponse(Response response) {
        responseStatus = response.getStatus();
        responseBody = response.readEntity(String.class);
    }

    private List<String> getExpectedDetailsFor(final String attributeName, final List<Map<String, String>> expectedDetails) {
        return expectedDetails.stream().map(detail -> detail.get(attributeName)).collect(toList());
    }

    @Given("^I add an offer in the kinitic shop for the '(.+)' currency with the following details:$")
    public void iAddAnOfferInTheKiniticShop(final String currencyName, final DataTable dataTable) throws Throwable {

        // this works, not pleasant but don't have time to do something nicer
        String jsonTemplate = "{ \"name\": \"%s\", \"category\": \"%s\", \"startDate\": \"%s\", \"expiryDate\": \"%s\", \"price\": \"%s\" }";

        // TODO: take out the hardcoding of the currencyId
        WebTarget webTarget = KINITIC_SHOP_API_BASE_URI.path("currencies/2");

        final List<Map<String, String>> offersToAdd = dataTable.asMaps();

        offersToAdd.forEach( offer -> {
            final String name = offer.get("name");
            final String category = offer.get("category");
            final String startDate = offer.get("startDate");
            final String expiryDate = offer.get("expiryDate");
            final String price = offer.get("price");

            final String offerAsJson = format(jsonTemplate, name, category, startDate, expiryDate, price);
            Response response = webTarget.path("offers").request().post(json(offerAsJson));
            readResponse(response);
        });
    }
}
