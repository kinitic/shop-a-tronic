package com.shop.kinitic.views;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.shop.kinitic.entity.Currency;
import org.junit.Test;

public class CurrencyViewTest {
    
    @Test
    public void shouldReturnCorrectlyFormattedCurrencyLink() throws Exception {
        Currency currency = mock(Currency.class);
        when(currency.getName()).thenReturn("GBP");
        when(currency.getDescription()).thenReturn("this is Pounds");
        when(currency.getId()).thenReturn(123L);

        final CurrencyView currencyView = new CurrencyView(currency);

        assertThat(currencyView.getName(), is("GBP"));
        assertThat(currencyView.getDescription(), is("this is Pounds"));
        assertThat(currencyView.getId(), is(123L));
        assertThat(currencyView.getLink(), is("http://localhost:8080/kinitic-shop/currencies/123/offers"));   // embeds the id into the link url
    }
}