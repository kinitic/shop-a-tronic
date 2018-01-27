package com.shop.kinitic.resources;

import java.util.List;

import com.shop.kinitic.services.CurrencyService;
import com.shop.kinitic.views.CurrencyView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kinitic-shop")
public class CurrencyResource {

    private CurrencyService currencyService;

    @Autowired
    public CurrencyResource(final CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "currencies")
    public CurrencyView getCurrencies() {
        final List<String> currencies = currencyService.getCurrencies();
        return new CurrencyView(currencies);
    }
}
