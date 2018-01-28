package com.shop.kinitic.resources;

import java.util.List;

import com.shop.kinitic.views.OfferView;

public class OffersView {

    private final String name;
    private final List<OfferView> offers;

    public OffersView(final String name, final List<OfferView> offers) {
        this.name = name;
        this.offers = offers;
    }

    public String getName() {
        return name;
    }

    public List<OfferView> getOffers() {
        return offers;
    }
}
