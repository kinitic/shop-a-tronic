package com.shop.kinitic.services;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.repository.CurrencyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceTest {

    @InjectMocks
    private CurrencyService currencyService;

    @Mock
    private CurrencyRepository currencyRepository;

    @Test
    public void shouldReturnAllAvailableCurrencies_whenCallingGetCurrencies() {
        final Currency gbpCurrency = new Currency("GBP", "Pounds Stering");
        final Currency usdCurrency = new Currency("USD", "US Dollars");
        final Currency swissCurrency = new Currency("SFr", "Swiss Franc");

        when(currencyRepository.findAll()).thenReturn(asList(gbpCurrency, usdCurrency, swissCurrency));

        final List<Currency> currencies = currencyService.getCurrencies();

        assertThat(currencies, hasSize(3));
        assertThat(currencies, containsInAnyOrder(gbpCurrency, usdCurrency, swissCurrency));
        
        verify(currencyRepository).findAll();
    }

    @Test
    public void shouldReturnNull_whenCallingFindCurrencyBy_andUnknownCurrencyIdIsPassedIn() {
        final long unknownCurrencyId = 999L;

        when(currencyRepository.findById(unknownCurrencyId)).thenReturn(null);

        assertThat(currencyService.findCurrencyBy(unknownCurrencyId), nullValue());
        verify(currencyRepository).findById(eq(unknownCurrencyId));
    }

    @Test
    public void shouldReturnCurrency_whenCallingFindCurrencyBy_withExistingCurrencyId() {
        final long existingCurrencyId = 999L;

        final Currency currency = new Currency("ABC", "valid currency");
        when(currencyRepository.findById(existingCurrencyId)).thenReturn(currency);

        assertThat(currencyService.findCurrencyBy(existingCurrencyId), is(currency));
        verify(currencyRepository).findById(eq(existingCurrencyId));
    }
}