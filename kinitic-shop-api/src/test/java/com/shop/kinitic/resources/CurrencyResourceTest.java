package com.shop.kinitic.resources;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.services.CurrencyService;
import com.shop.kinitic.views.CurrencyView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyResourceTest {

    @InjectMocks
    private CurrencyResource currencyResource;

    @Mock
    private CurrencyService currencyService;

    @Test
    public void shouldReturnListOfAllAvailableCurrencies_whenInvokingGetCurrencies() throws Exception {
        final Currency gbpCurrency = new Currency("GBP", "Pounds Stering");
        final Currency usdCurrency = new Currency("USD", "US Dollars");
        final Currency swissCurrency = new Currency("SFr", "Swiss Franc");

        when(currencyService.getCurrencies()).thenReturn(asList(gbpCurrency, usdCurrency, swissCurrency));
        final CurrencyView view = currencyResource.getCurrencies();

        assertThat(view.getCurrencies(), hasSize(3));
        assertThat(view.getCurrencies(), containsInAnyOrder(gbpCurrency, usdCurrency, swissCurrency));

        verify(currencyService).getCurrencies();
    }

    @Test
    public void shouldReturnEmptyListOffers_whenInvokingGetOffers_WithNullAsCurrencyName() throws Exception {
        assertThat(currencyResource.getOffers(null), empty());
    }

    @Test
    public void shouldReturnEmptyListOffers_whenInvokingGetOffers_WithUnknownCurrencyName() throws Exception {
        assertThat(currencyResource.getOffers("whatever"), empty());
    }

    @Test
    public void shouldReturnListOfAllOffersForAGivenCurrency_whenInvokingGetOffers() throws Exception {
        
    }
}