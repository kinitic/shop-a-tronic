package com.shop.kinitic.services;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        assertThat(currencyService.getCurrencies(), hasSize(3));
        
        verify(currencyRepository).findAll();
    }
}