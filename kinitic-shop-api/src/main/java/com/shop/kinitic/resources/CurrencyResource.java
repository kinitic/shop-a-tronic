package com.shop.kinitic.resources;

import static java.util.Collections.emptyList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.services.CurrencyService;
import com.shop.kinitic.views.CurrencyView;
import com.shop.kinitic.views.OfferView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
        final List<Currency> currencies = currencyService.getCurrencies();
        return new CurrencyView(currencies);
    }

    @RequestMapping(method = RequestMethod.GET, path = "currencies/{currencyId}/offers")
    public List<OfferView> getOffers(@PathVariable final String currencyId) {
        return emptyList();
    }
}
