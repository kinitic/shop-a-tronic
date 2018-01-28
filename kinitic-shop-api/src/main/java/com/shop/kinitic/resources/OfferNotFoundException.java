package com.shop.kinitic.resources;

import static java.lang.String.format;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OfferNotFoundException extends RuntimeException {

    public OfferNotFoundException(final String currencyName, final Long offerId) {
        super(format("could not find offerId: %s for currency: %s", offerId, currencyName));
    }
}
