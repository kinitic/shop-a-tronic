package com.shop.kinitic.views;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.shop.kinitic.entity.OfferDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OfferViewTest {

    @Mock
    private OfferDetails offerDetails;

    @Test
    public void shouldReturnCorrectlyFormattedOfferLink() {
        when(offerDetails.getId()).thenReturn(456L);
        when(offerDetails.getName()).thenReturn("some offer");
        when(offerDetails.getCategory()).thenReturn("some department");
        when(offerDetails.getPrice()).thenReturn(BigDecimal.valueOf(0.99));
        when(offerDetails.getStartDate()).thenReturn(LocalDate.of(2017, 12, 10));
        when(offerDetails.getExpiryDate()).thenReturn(LocalDate.of(2018, 12, 10));

        final OfferView offerView = new OfferView(offerDetails, 123L);

        assertThat(offerView.getLink(), is("http://localhost:8080/kinitic-shop/currencies/123/offers/456"));
        
        verify(offerDetails, times(2)).getId();
    }
}