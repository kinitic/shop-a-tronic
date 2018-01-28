package com.shop.kinitic.services;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.entity.OfferDetails;
import com.shop.kinitic.repository.OfferRepository;
import com.shop.kinitic.views.OfferView;
import org.junit.After;
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

    @After
    public void tearDown() {
        verifyCurrencyInvocations();
    }

    @Test
    public void shouldReturnEmptyListForCurrency_thatHasNoAssociatedOffers() {
        when(offerRepository.findByCurrency(any(Currency.class))).thenReturn(emptyList());

        assertThat(offerService.getAllOffersFor(new Currency("GBP", "British Pounds")), empty());

        verify(offerRepository).findByCurrency(currencyArgumentCaptor.capture());
    }

    @Test
    public void shouldReturnAllOffersFoundForASpecificCurrency() {
        final Currency currency = new Currency("GBP", "British Pounds");
        final OfferDetails offerDetails = new OfferDetails(currency, "offerName", "category", LocalDate.now(), LocalDate.now().plusDays(3), BigDecimal.valueOf(2.99));

        when(offerRepository.findByCurrency(any(Currency.class))).thenReturn(singletonList(offerDetails));

        final List<OfferView> offersView = offerService.getAllOffersFor(currency);

        assertThat(offersView, hasSize(1));
        assertThat(offersView.get(0).getName(), is("offerName"));
        assertThat(offersView.get(0).getCategory(), is("category"));
        assertThat(offersView.get(0).getStartDate(), notNullValue());
        assertThat(offersView.get(0).getEndDate(), notNullValue());
        assertThat(offersView.get(0).getPrice(), is(BigDecimal.valueOf(2.99)));

        verify(offerRepository).findByCurrency(currencyArgumentCaptor.capture());
    }

    private void verifyCurrencyInvocations() {
        final Currency captorValue = currencyArgumentCaptor.getValue();
        assertThat(captorValue.getName(), is("GBP"));
        assertThat(captorValue.getDescription(), is("British Pounds"));
    }
}