package com.shop.kinitic.acceptance;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;
import static com.jayway.jsonpath.JsonPath.compile;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.withJsonPath;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Map;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.jayway.jsonpath.Filter;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class KiniticShopStepdefs {

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
        final List<Map<String, String>> currencyDetails = dataTable.asMaps();

        final List<String> expectedCurrencyNames = getCurrencyNames("name", currencyDetails); // returns GBP, USD in list

        expectedCurrencyNames.forEach(expectedCurrencyName -> {
                    final Map<String, String> expectedCurrencyDetailsMap = currencyDetails.stream().filter(currencyDetail -> currencyDetail.get("name").equals(expectedCurrencyName)).findFirst().get();

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

    @When("^I click the 'GBP' offers link$")
    public void iClickTheGBPOffersLink() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }




    private void readResponse(Response response) {
        responseStatus = response.getStatus();
        responseBody = response.readEntity(String.class);
    }

    private List<String> getCurrencyNames(final String attributeName, final List<Map<String, String>> currencyDetails) {
        return currencyDetails.stream().map(currencyDetail -> currencyDetail.get(attributeName)).collect(toList());
    }

    @Then("^I should see the following offer entries:$")
    public void iShouldSeeTheFollowingOfferEntries() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
