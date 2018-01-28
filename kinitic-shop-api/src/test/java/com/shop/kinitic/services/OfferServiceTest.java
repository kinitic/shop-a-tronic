package com.shop.kinitic.services;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.of;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.entity.OfferDetails;
import com.shop.kinitic.model.Offer;
import com.shop.kinitic.repository.OfferRepository;
import com.shop.kinitic.resources.OffersView;
import com.shop.kinitic.views.OfferView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceTest {

    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferRepository offerRepository;

    @Captor
    private ArgumentCaptor<Currency> currencyArgumentCaptor;

    @Test
    public void shouldReturnEmptyListForCurrency_thatHasNoAssociatedOffers() {
        when(offerRepository.findByCurrency(any(Currency.class))).thenReturn(emptyList());

        final OffersView offersView = offerService.getAllOffersFor(new Currency("GBP", "British Pounds"));

        assertThat(offersView.getName(), is("GBP"));
        assertThat(offersView.getActiveOffers(), empty());
        assertThat(offersView.getExpiredOffers(), empty());

        verify(offerRepository).findByCurrency(currencyArgumentCaptor.capture());
        verifyCurrencyInvocations();
    }

    @Test
    public void shouldReturnAllOffersFoundForASpecificCurrency_andGroupByActiveAndExpiredOffers() {
        final Currency currency = new Currency("GBP", "British Pounds");
        final OfferDetails offerDetails = new OfferDetails(currency, "offerName", "category", LocalDate.now().minusDays(2), LocalDate.now().plusDays(3), valueOf(2.99));

        when(offerRepository.findByCurrency(any(Currency.class))).thenReturn(singletonList(offerDetails));

        final OffersView offersView = offerService.getAllOffersFor(currency);

        assertThat(offersView.getName(), is("GBP"));
        assertThat(offersView.getActiveOffers(), hasSize(1));
        assertThat(offersView.getExpiredOffers(), empty());

        verify(offerRepository).findByCurrency(currencyArgumentCaptor.capture());
        verifyCurrencyInvocations();
    }

    @Test
    public void shouldReturnMatchedOfferForSpecificCurrencyAndOffer() {
        final Currency currency = mock(Currency.class);

        final OfferDetails offerDetails = mock(OfferDetails.class);

        when(offerRepository.findByCurrencyAndId(currency, 123L)).thenReturn(offerDetails);
        when(currency.getId()).thenReturn(987L);

        when(offerDetails.getId()).thenReturn(123L);
        when(offerDetails.getName()).thenReturn("offerName");
        when(offerDetails.getCategory()).thenReturn("category");
        when(offerDetails.getStartDate()).thenReturn(of(2016, 1, 1));
        when(offerDetails.getExpiryDate()).thenReturn(of(2017, 1, 1));
        when(offerDetails.getPrice()).thenReturn(valueOf(12.99));

        final OfferView offerView = offerService.getOfferFor(currency, 123L);

        assertThat(offerView.getId(), is(123L));

        verify(offerRepository).findByCurrencyAndId(any(Currency.class), eq(123L));
        verifyOfferDetails(offerDetails);
    }

    @Test
    public void shouldReturnOfferIdOfTheNewlySavedOffer() {
        final Currency currency = new Currency("GBP", "British Pounds");
        final Offer offer = new Offer("offerName", "category", "2012-01-01", "2020-01-01", valueOf(2.99));

        final OfferDetails offerDetails = mock(OfferDetails.class);

        when(offerRepository.save(any(OfferDetails.class))).thenReturn(offerDetails);
        when(offerDetails.getId()).thenReturn(123L);

        assertThat(offerService.addOffer(currency, offer), is(123L)); // should return the new offer id from the save call

        verify(offerDetails).getId();
        verify(offerRepository).save(any(OfferDetails.class));
    }

    @Test
    public void shouldNotUpdateOffer_whenOfferCannotBeFoundForThatCurrency() {
        final Currency currency = new Currency("GBP", "British Pounds");
        final Offer offer = new Offer("offerName", "category", "2012-01-01", "2020-01-01", valueOf(2.99));
        final Long offerId = 123L;

        when(offerRepository.findByCurrencyAndId(currency, offerId)).thenReturn(null);

        assertThat(offerService.updateOffer(currency, offerId, offer), nullValue());

        verify(offerRepository).findByCurrencyAndId(eq(currency), eq(offerId));
        verifyNoMoreInteractions(offerRepository);
    }

    @Test
    public void shouldUpdateOffer_whenOfferExistsForThatCurrency() {
        final Currency currency = new Currency("GBP", "British Pounds");
        final Offer offer = new Offer("offerName", "category", "2012-01-01", "2020-01-01", valueOf(2.99));
        final Long offerId = 123L;

        final OfferDetails offerDetails = mock(OfferDetails.class);

        when(offerRepository.findByCurrencyAndId(currency, offerId)).thenReturn(offerDetails);
        when(offerRepository.save(any(OfferDetails.class))).thenReturn(offerDetails);
        when(offerDetails.getId()).thenReturn(123L);

        assertThat(offerService.updateOffer(currency, offerId, offer), is(123L));

        verify(offerRepository).findByCurrencyAndId(eq(currency), eq(offerId));
        verify(offerRepository).save(any(OfferDetails.class));
        verify(offerDetails).getId();
    }

    private void verifyOfferDetails(OfferDetails offerDetails) {
        verify(offerDetails, times(2)).getId();
        verify(offerDetails).getName();
        verify(offerDetails).getCategory();
        verify(offerDetails).getStartDate();
        verify(offerDetails).getExpiryDate();
        verify(offerDetails).getPrice();
    }

    private void verifyCurrencyInvocations() {
        final Currency captorValue = currencyArgumentCaptor.getValue();
        assertThat(captorValue.getName(), is("GBP"));
        assertThat(captorValue.getDescription(), is("British Pounds"));
    }
}