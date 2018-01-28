package com.shop.kinitic.resources;

import java.util.List;

import com.shop.kinitic.views.OfferView;

public class OffersView {

    private final String name;
    private final List<OfferView> activeOffers;
    private final List<OfferView> expiredOffers;

    public OffersView(final String name, final List<OfferView> activeOffers, final List<OfferView> expiredOffers) {
        this.name = name;
        this.activeOffers = activeOffers;
        this.expiredOffers = expiredOffers;
    }

    public String getName() {
        return name;
    }

    public List<OfferView> getActiveOffers() {
        return activeOffers;
    }

    public List<OfferView> getExpiredOffers() {
        return expiredOffers;
    }
}
