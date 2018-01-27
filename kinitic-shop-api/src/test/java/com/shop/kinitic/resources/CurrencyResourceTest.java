package com.shop.kinitic.resources;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

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
        when(currencyService.getCurrencies()).thenReturn(asList("GBP", "USD", "Euro"));
        final CurrencyView view = currencyResource.getCurrencies();

        assertThat(view.getCurrencies(), hasSize(3));
    }
}