package com.shop.kinitic.services;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.entity.OfferDetails;
import com.shop.kinitic.repository.OfferRepository;
import com.shop.kinitic.views.OfferView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferService {

    private final OfferRepository offerRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<OfferView> getAllOffersFor(final Currency currency) {
        final List<OfferDetails> offers = offerRepository.findByCurrency(currency);

        return offers.stream()
                .map(offer -> new OfferView(offer.getName(), offer.getCategory(), offer.getStartDate(), offer.getExpiryDate(), offer.getPrice()))
                .collect(toList());
    }
}
