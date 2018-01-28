package com.shop.kinitic.views;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

import com.shop.kinitic.entity.Currency;

public class CurrencyView {

    private static final String KINITIC_BASE_URL = "http://localhost:8080/kinitic-shop/currencies/%d/offers";

    private final Long id;
    private final String name;
    private final String description;
    private final String link;

    public CurrencyView(final Currency currency) {
        this.id = currency.getId();
        this.name = currency.getName();
        this.description = currency.getDescription();
        this.link = format(KINITIC_BASE_URL, currency.getId());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }
}
