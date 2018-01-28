package com.shop.kinitic.resources;

import java.util.List;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.services.CurrencyService;
import com.shop.kinitic.services.OfferService;
import com.shop.kinitic.views.CurrenciesView;
import com.shop.kinitic.views.CurrencyView;
import com.shop.kinitic.views.OfferView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kinitic-shop/currencies")
public class CurrencyResource {

    private CurrencyService currencyService;
    private OfferService offerService;

    @Autowired
    public CurrencyResource(final CurrencyService currencyService, final OfferService offerService) {
        this.currencyService = currencyService;
        this.offerService = offerService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public CurrenciesView getCurrencies() {
        final List<CurrencyView> currencies = currencyService.getCurrencies();

        return new CurrenciesView(currencies);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{currencyId}/offers")
    public OffersView getOffers(@PathVariable final Long currencyId) {

        final Currency currency = currencyService.findCurrencyBy(currencyId);
        if (currency == null) {
            throw new CurrencyNotFoundException(currencyId);
        }
        
        final List<OfferView> offers = offerService.getAllOffersFor(currency);

        return new OffersView(currency.getName(), offers);
    }
}
