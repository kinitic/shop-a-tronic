package com.shop.kinitic.resources;

import static java.lang.String.format;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.of;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.entity.OfferDetails;
import com.shop.kinitic.model.Offer;
import com.shop.kinitic.services.CurrencyService;
import com.shop.kinitic.services.OfferService;
import com.shop.kinitic.views.ActiveOfferView;
import com.shop.kinitic.views.CurrenciesView;
import com.shop.kinitic.views.CurrencyView;
import com.shop.kinitic.views.ExpiredOfferView;
import com.shop.kinitic.views.OfferView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    public void shouldReturnActiveAndExpiredListsOfAllOffersForAValidCurrency_whenInvokingGetOffers() throws Exception {
        final Currency currency = new Currency("GBP", "Pounds Sterling");

        when(currencyService.findCurrencyBy(1L)).thenReturn(currency);

        // active offers
        OfferView active1 = new ActiveOfferView(new OfferDetails(currency, "name1", "category1", of(2018, 1, 1), of(2020, 1, 1), valueOf(10.99)), currency.getId());
        OfferView active2 = new ActiveOfferView(new OfferDetails(currency, "name2", "category2", of(2017, 11, 21), of(2020, 1, 1), valueOf(1.99)), currency.getId());
        OfferView active3 = new ActiveOfferView(new OfferDetails(currency, "name3", "category3", of(2016, 4, 11), of(2020, 1, 1), valueOf(5.99)), currency.getId());

        // expired offer
        OfferView expired1 = new ExpiredOfferView(new OfferDetails(currency, "name4", "category4", of(2018, 1, 1), of(2018, 1, 20), valueOf(10.99)), currency.getId());

        when(offerService.getAllOffersFor(currency)).thenReturn(new OffersView("GBP", asList(active1, active2, active3), singletonList(expired1)));

        final OffersView view = currencyResource.getOffers(1L);

        assertThat(view.getName(), is("GBP"));
        assertThat(view.getActiveOffers(), hasSize(3));
        assertThat(view.getActiveOffers(), containsInAnyOrder(active1, active2, active3));
        assertThat(view.getExpiredOffers(), hasSize(1));
        assertThat(view.getExpiredOffers(), containsInAnyOrder(expired1));

        verify(currencyService).findCurrencyBy(eq(1L));
        verify(offerService).getAllOffersFor(currency);
    }

    @Test
    public void shouldReturnSingleOfferView_whenInvokingGetOffer_withValidCurrencyIdAndOfferId() {
        final Currency currency = new Currency("GBP", "Pounds Sterling");
        when(currencyService.findCurrencyBy(1L)).thenReturn(currency);

        OfferView offer = new OfferView(new OfferDetails(currency, "name1", "category1", of(2018, 1, 1), of(2020, 1, 1), valueOf(10.99)), currency.getId());
        when(offerService.getOfferFor(currency, 123L)).thenReturn(offer);

        assertThat(currencyResource.getOffer(1L, 123L), is(offer));

        verify(currencyService).findCurrencyBy(eq(1L));
        verify(offerService).getOfferFor(eq(currency), eq(123L));
    }

    @Test
    public void shouldNotAddOffer_whenPostingNewOfferToAnUnknownCurrency() {
        final long unknownCurrencyId = 123L;

        when(currencyService.findCurrencyBy(unknownCurrencyId)).thenReturn(null);

        final ResponseEntity responseEntity = currencyResource.addOffer(unknownCurrencyId, new Offer("new offer", "new category", "2015-01-01", "2020-01-02", valueOf(12.00)));

        assertThat(responseEntity, is(ResponseEntity.noContent().build()));
        verify(currencyService).findCurrencyBy(eq(unknownCurrencyId));
    }

    @Test
    public void shouldAddOffer_whenPostingNewOfferToAnExistingCurrency() {
        final Currency currency = mock(Currency.class);
        final Offer offer = new Offer("new offer", "new category", "2015-01-01", "2020-01-02", valueOf(12.00));

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(currencyService.findCurrencyBy(123L)).thenReturn(currency);

        final long offerId = 101L;
        when(offerService.addOffer(currency, offer)).thenReturn(offerId); // returns the offer id for the newly added offer

        final ResponseEntity responseEntity = currencyResource.addOffer(123L, offer);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(responseEntity.getHeaders().get("location").get(0), is(format("http://localhost/%d", offerId)));   // uses the offer id in the location header
    }

    @Test
    public void shouldNotUpdateOffer_whenUpdatingOfferToAnUnknownCurrency() {
        final Long unknownCurrencyId = 123L;
        when(currencyService.findCurrencyBy(unknownCurrencyId)).thenReturn(null);

        final ResponseEntity responseEntity = currencyResource.updateOffer(unknownCurrencyId, 1L, new Offer());

        assertThat(responseEntity, is(ResponseEntity.noContent().build()));
        verify(currencyService).findCurrencyBy(eq(unknownCurrencyId));
    }

    @Test(expected = OfferNotFoundException.class)
    public void shouldThrowException_whenInvokingUpdateOffer_WithUnknownOfferId() {
        final Long unknownOfferId = 987L;

        final Offer offer = new Offer();
        final Currency currency = new Currency("GBP", "Pounds Sterling");
        when(currencyService.findCurrencyBy(123L)).thenReturn(currency);
        when(offerService.updateOffer(currency, unknownOfferId, offer)).thenReturn(null);

        final ResponseEntity responseEntity = currencyResource.updateOffer(123L, unknownOfferId, offer);

        assertThat(responseEntity, is(ResponseEntity.notFound().build()));
    }

    @Test
    public void shouldUpdateOffer_whenUpdatingOffer_happyPath() {
        final Offer offer = new Offer();
        final Currency currency = new Currency("GBP", "Pounds Sterling");
        final long offerIdToUpdate = 987L;

        when(currencyService.findCurrencyBy(123L)).thenReturn(currency);
        when(offerService.updateOffer(currency, offerIdToUpdate, offer)).thenReturn(offerIdToUpdate);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final ResponseEntity responseEntity = currencyResource.updateOffer(123L, offerIdToUpdate, offer);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }
}