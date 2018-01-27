package com.shop.kinitic.views;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

import com.shop.kinitic.entity.Currency;

public class CurrencyView {

    private static final String KINITIC_BASE_URL = "http://localhost:8080/kinitic-shop/currency/%d/offers";

    private List<CurrencyDetails> currencies;

    public CurrencyView(final List<Currency> currencies) {
        this.currencies = new ArrayList<>();

        if (currencies == null)
            return;
        
        currencies.forEach(currency ->
                this.currencies.add(new CurrencyDetails(currency, format(KINITIC_BASE_URL, currency.getId())))
        );
    }
    
    public List<CurrencyDetails> getCurrencies() {
        return currencies;
    }

    protected class CurrencyDetails {

        private final Long id;
        private final String name;
        private final String description;
        private final String link;

        CurrencyDetails(final Currency currency, final String link) {
            this.name = currency.getName();
            this.id = currency.getId();
            this.description = currency.getDescription();
            this.link = link;
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
}
