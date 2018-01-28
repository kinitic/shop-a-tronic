package com.shop.kinitic.resources;

import static org.springframework.http.ResponseEntity.noContent;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.model.Offer;
import com.shop.kinitic.services.CurrencyService;
import com.shop.kinitic.services.OfferService;
import com.shop.kinitic.views.CurrenciesView;
import com.shop.kinitic.views.CurrencyView;
import com.shop.kinitic.views.OfferView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

        return offerService.getAllOffersFor(currency);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{currencyId}/offers/{offerId}")
    public OfferView getOffer(@PathVariable final Long currencyId, @PathVariable final Long offerId) {

        final Currency currency = currencyService.findCurrencyBy(currencyId);
        if (currency == null) {
            throw new CurrencyNotFoundException(currencyId);
        }

        return offerService.getOfferFor(currency, offerId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{currencyId}/offers")
    public ResponseEntity<?> addOffer(@PathVariable final Long currencyId, @RequestBody final Offer offer) {

        final Currency currency = currencyService.findCurrencyBy(currencyId);

        if (currency == null) {
            return noContent().build();
        }

        final Long offerId = offerService.addOffer(currency, offer);

        URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(offerId).toUri();

        return ResponseEntity.created(location).build();
    }
}
