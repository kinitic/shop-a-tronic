package com.shop.kinitic.acceptance;

import static com.jayway.jsonassert.JsonAssert.with;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.withJsonPath;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Map;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class KiniticShopStepdefs {

    private static final WebTarget KINITIC_SHOP_API_BASE_URI = newClient().target("http://localhost:8080/kinitic-shop");

    private int responseStatus;
    private String responseBody;

    @Given("^I invoke the currencies Api endpoint$")
    public void iInvokeTheCurrenciesApiEndpoint() throws Throwable {
        final Response response = KINITIC_SHOP_API_BASE_URI.path("/currencies").request().get();
        readResponse(response);
    }

    @And("^the response is (\\d+)$")
    public void responseStatusIs(final int status) {
        assertThat(status, is(responseStatus));
    }

    @And("^the response contains the following currency entries:$")
    public void theResponseContainsTheFollowingCurrencyEntries(final DataTable dataTable) throws Throwable {
        final List<Map<String, String>> currencyDetails = dataTable.asMaps();

        final List<String> expectedCurrencyNames = getExpectedValuesFor("name", currencyDetails);
        final List<String> expectedCurrencyDescription = getExpectedValuesFor("description", currencyDetails);

        assertThat(responseBody, isJson(allOf(
                withJsonPath("$.currencies", hasSize(currencyDetails.size())),
                withJsonPath("$.currencies[*].name", hasItems(expectedCurrencyNames.toArray())),
                withJsonPath("$.currencies[*].description", hasItems(expectedCurrencyDescription.toArray())))
        ));
    }

    @And("^the id exists$")
    public void theIdExists() throws Throwable {
        with(responseBody)
                .assertThat("$.currencies[*].id", notNullValue());
    }

    private void readResponse(Response response) {
        responseStatus = response.getStatus();
        responseBody = response.readEntity(String.class);
    }


    private List<String> getExpectedValuesFor(String attributeName, List<Map<String, String>> currencyDetails) {
        return currencyDetails.stream().map(currencyDetail -> currencyDetail.get(attributeName)).collect(toList());
    }
}
