package com.shop.kinitic.resources;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.entity.OfferDetails;
import com.shop.kinitic.services.CurrencyService;
import com.shop.kinitic.services.OfferService;
import com.shop.kinitic.views.CurrenciesView;
import com.shop.kinitic.views.CurrencyView;
import com.shop.kinitic.views.OfferView;
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

    @Mock
    private OfferService offerService;

    @Test
    public void shouldReturnListOfAllAvailableCurrencies_whenInvokingGetCurrencies() throws Exception {
        final CurrencyView gbpCurrency = mock(CurrencyView.class);
        final CurrencyView usdCurrency = mock(CurrencyView.class);
        final CurrencyView swissCurrency = mock(CurrencyView.class);

        when(currencyService.getCurrencies()).thenReturn(asList(gbpCurrency, usdCurrency, swissCurrency));
        final CurrenciesView currencies = currencyResource.getCurrencies();

        assertThat(currencies.getCurrencies(), hasSize(3));

        verify(currencyService).getCurrencies();
    }

    @Test(expected = CurrencyNotFoundException.class)
    public void shouldThrowException_whenInvokingGetOffers_WithUnknownCurrencyId() throws Exception {
        final long unknownCurrencyId = 6L;
        when(currencyService.findCurrencyBy(unknownCurrencyId)).thenReturn(null);

        currencyResource.getOffers(unknownCurrencyId);

        verifyZeroInteractions(offerService);
    }

    @Test
    public void shouldReturnListOfAllOffersForAValidCurrency_whenInvokingGetOffers() throws Exception {
        final Currency currency = new Currency("GBP", "Pounds Sterling");

        when(currencyService.findCurrencyBy(1L)).thenReturn(currency);

        OfferView offer1 = new OfferView(new OfferDetails(currency, "name1", "category1", LocalDate.of(2018, 1, 1), LocalDate.of(2020, 1, 1), BigDecimal.valueOf(10.99)), currency.getId());
        OfferView offer2 = new OfferView(new OfferDetails(currency, "name2", "category2", LocalDate.of(2017, 11, 21), LocalDate.of(2020, 1, 1), BigDecimal.valueOf(1.99)), currency.getId());
        OfferView offer3 = new OfferView(new OfferDetails(currency, "name3", "category3", LocalDate.of(2016, 4, 11), LocalDate.of(2020, 1, 1), BigDecimal.valueOf(5.99)), currency.getId());

        when(offerService.getAllOffersFor(currency)).thenReturn(asList(offer1, offer2, offer3));

        final OffersView view = currencyResource.getOffers(1L);
        assertThat(view.getOffers(), hasSize(3));
        assertThat(view.getOffers(), containsInAnyOrder(offer1, offer2, offer3));
        assertThat(view.getName(), is("GBP"));

        verify(currencyService).findCurrencyBy(eq(1L));
        verify(offerService).getAllOffersFor(currency);
    }

    @Test
    public void shouldReturnSingleOfferView_whenInvokingGetOffer_withValidCurrencyIdAndOfferId() {
        final Currency currency = new Currency("GBP", "Pounds Sterling");
        when(currencyService.findCurrencyBy(1L)).thenReturn(currency);

        OfferView offer = new OfferView(new OfferDetails(currency, "name1", "category1", LocalDate.of(2018, 1, 1), LocalDate.of(2020, 1, 1), BigDecimal.valueOf(10.99)), currency.getId());
        when(offerService.getOfferFor(currency, 123L)).thenReturn(offer);

        assertThat(currencyResource.getOffer(1L, 123L), is(offer));

        verify(currencyService).findCurrencyBy(eq(1L));
        verify(offerService).getOfferFor(eq(currency), eq(123L));
    }
}