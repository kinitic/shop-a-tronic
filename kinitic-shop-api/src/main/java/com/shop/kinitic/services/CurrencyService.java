package com.shop.kinitic.services;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.repository.CurrencyRepository;
import com.shop.kinitic.views.CurrencyView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrencyService {
    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<CurrencyView> getCurrencies() {
        final List<Currency> currencies = currencyRepository.findAll();

        return currencies.stream()
                .map(CurrencyView::new)
                .collect(toList());
    }

    public Currency findCurrencyBy(final Long currencyId) {
        return currencyRepository.findById(currencyId);
    }
}
