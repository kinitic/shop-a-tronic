package com.shop.kinitic.acceptance;

import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class KiniticShopStepdefs {

    private static final WebTarget KINITIC_SHOP_API_BASE_URI = newClient().target("http://localhost:8080/kinitic-shop");

    private int responseStatus;
    private String responseBody;

    @Given("^I invoke the currencies Api endpoint$")
    public void I_invoke_the_currencies_api_endpoint() throws Throwable {
        final Response response = KINITIC_SHOP_API_BASE_URI.path("/currencies").request().get();
        readResponse(response);
    }

    @And("^the response is (\\d+)$")
    public void responseStatusIs(int status) {
        assertThat(status, is(responseStatus));
    }

    private void readResponse(Response response) {
        responseStatus = response.getStatus();
        responseBody = response.readEntity(String.class);
    }
}
