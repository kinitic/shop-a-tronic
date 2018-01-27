package com.shop.kinitic.services;

import static java.util.Collections.emptyList;

import java.util.List;

import com.shop.kinitic.entity.Currency;
import org.springframework.stereotype.Component;

@Component
public class CurrencyService {
    public List<Currency> getCurrencies() {
        return emptyList();
    }
}
