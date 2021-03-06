package com.shop.kinitic.services;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.entity.OfferDetails;
import com.shop.kinitic.model.Offer;
import com.shop.kinitic.repository.OfferRepository;
import com.shop.kinitic.resources.OffersView;
import com.shop.kinitic.views.ActiveOfferView;
import com.shop.kinitic.views.ExpiredOfferView;
import com.shop.kinitic.views.OfferView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferService {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private final OfferRepository offerRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public OffersView getAllOffersFor(final Currency currency) {
        final List<OfferDetails> offers = offerRepository.findByCurrency(currency);


        final Predicate<OfferDetails> activeOfferPredicate = offer -> offer.getStartDate().isBefore(LocalDate.now()) && offer.getExpiryDate().isAfter(LocalDate.now());

        final Map<Boolean, List<OfferDetails>> activeAndExpiredOffers = offers.stream()
                .collect(partitioningBy(activeOfferPredicate));

        final List<OfferDetails> activeOffers = activeAndExpiredOffers.get(true);
        final List<OfferDetails> expiredOffers = activeAndExpiredOffers.get(false);

        final List<OfferView> activeOfferView = activeOffers.stream()
                .map(offer -> new ActiveOfferView(offer, currency.getId()))
                .collect(toList());

        final List<OfferView> expiredOfferView = expiredOffers.stream()
                .map(offer -> new ExpiredOfferView(offer, currency.getId()))
                .collect(toList());

        return new OffersView(currency.getName(), activeOfferView, expiredOfferView);
    }

    public OfferView getOfferFor(final Currency currency, final Long offerId) {
        final OfferDetails offerDetails = offerRepository.findByCurrencyAndId(currency, offerId);

        return new OfferView(offerDetails, currency.getId());
    }

    public Long addOffer(final Currency currency, final Offer offer) {
        final OfferDetails offerDetails = new OfferDetails(currency, offer.getName(),
                offer.getCategory(), formatDate(offer.getStartDate()), formatDate(offer.getExpiryDate()), offer.getPrice());

        return offerRepository.save(offerDetails).getId();
    }

    public Long updateOffer(final Currency currency, final Long offerId, final Offer offer) {
        final OfferDetails offerDetails = offerRepository.findByCurrencyAndId(currency, offerId);

        if (offerDetails == null) {
            return null;
        }
        
        offerDetails.setName(offer.getName());
        offerDetails.setCategory(offer.getCategory());
        offerDetails.setStartDate(formatDate(offer.getStartDate()));
        offerDetails.setExpiryDate(formatDate(offer.getExpiryDate()));
        offerDetails.setPrice(offer.getPrice());

        return offerRepository.save(offerDetails).getId();
    }

    private LocalDate formatDate(final String date) {
        return parse(date, ofPattern(DATE_PATTERN));
    }
}
