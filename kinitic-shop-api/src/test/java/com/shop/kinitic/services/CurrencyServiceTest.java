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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.repository.CurrencyRepository;
import com.shop.kinitic.views.CurrencyView;
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
    public void shouldReturnAllAvailableCurrenciesInTheListView_whenCallingGetCurrencies() {
        final Currency gbpCurrency = mock(Currency.class);
        final Currency usdCurrency = mock(Currency.class);

        when(currencyRepository.findAll()).thenReturn(asList(gbpCurrency, usdCurrency));

        final List<CurrencyView> currenciesView = currencyService.getCurrencies();
        when(gbpCurrency.getName()).thenReturn("GBP");
        when(gbpCurrency.getDescription()).thenReturn("British pounds");
        when(gbpCurrency.getId()).thenReturn(123L);

        when(usdCurrency.getName()).thenReturn("USD");
        when(usdCurrency.getDescription()).thenReturn("US dollars");
        when(usdCurrency.getId()).thenReturn(987L);

        assertThat(currenciesView, hasSize(2));

        // TODO: add custom matcher to assert on the views themselves
        verify(currencyRepository).findAll();

        verify(gbpCurrency).getName();
        verify(gbpCurrency).getDescription();
        verify(gbpCurrency, times(2)).getId();
        
        verify(usdCurrency).getName();
        verify(usdCurrency).getDescription();
        verify(usdCurrency, times(2)).getId();
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