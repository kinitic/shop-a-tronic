package com.shop.kinitic.views;

import java.util.List;

import com.shop.kinitic.entity.Currency;

public class CurrencyView {

    private List<Currency> currencies;

    public CurrencyView(final List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }
}
