package com.shop.kinitic.views;

import java.util.List;

public class CurrencyView {

    private List<String> currencies;

    public CurrencyView(final List<String> currencies) {
        this.currencies = currencies;
    }

    public List<String> getCurrencies() {
        return currencies;
    }
}
