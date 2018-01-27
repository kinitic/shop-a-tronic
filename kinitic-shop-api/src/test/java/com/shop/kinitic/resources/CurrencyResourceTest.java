package com.shop.kinitic.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import com.shop.kinitic.views.CurrencyView;
import org.junit.Test;
import org.mockito.InjectMocks;

public class CurrencyResourceTest {

    @InjectMocks
    private CurrencyResource currencyResource;

    @Test
    public void shouldReturnListOfAllAvailableCurrencies_whenInvokingGetCurrencies() throws Exception {
        final CurrencyView currencies = currencyResource.getCurrencies();

        assertThat(currencies, notNullValue());
    }
}