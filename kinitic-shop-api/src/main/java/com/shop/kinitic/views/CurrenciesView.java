package com.shop.kinitic.views;

import java.util.List;

public class CurrenciesView  {
    private List<CurrencyView> currencies;

    public CurrenciesView(final List<CurrencyView> currencies) {
        this.currencies = currencies;
    }

    public List<CurrencyView> getCurrencies() {
        return currencies;
    }
}
