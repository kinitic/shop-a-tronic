package com.shop.kinitic.resources;

import static java.lang.String.format;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class CurrencyNotFoundException extends RuntimeException {

    public CurrencyNotFoundException(final Long currencyId) {
        super(format("could not find currencyId: %s", currencyId));
    }
}
