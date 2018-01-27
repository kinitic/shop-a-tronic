package com.shop.kinitic.views;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import com.shop.kinitic.entity.Currency;
import org.junit.Test;

public class CurrencyViewTest {
    
    @Test
    public void shouldReturnAllAvailableCurrenciesInView() throws Exception {
        Currency currency1 = new Currency("currency 1", "I am the first currency");
        Currency currency2 = new Currency("currency 2", "I am the second currency");
        Currency currency3 = new Currency("currency 3", "I am the third currency");

        final List<Currency> currencies = asList(currency1, currency2, currency3);

        final CurrencyView currencyView = new CurrencyView(currencies);

        final List<CurrencyView.CurrencyDetails> currencyDetails = currencyView.getCurrencies();
        assertThat(currencyDetails, hasSize(3));

        // TODO: add more assertions with custom type matcher
        // but for now will have assertions for the correct link generation using the auto-generated id in the cukes
    }

    @Test
    public void shouldReturnEmptyListIfCurrenciesPassedInIsNull() throws Exception {
        assertThat(new CurrencyView(null).getCurrencies(), empty());
    }

    @Test
    public void shouldReturnEmptyListIfCurrenciesIsEmptyList() throws Exception {
        assertThat(new CurrencyView(emptyList()).getCurrencies(), empty());
    }
}