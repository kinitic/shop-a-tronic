package com.shop.kinitic.views;

import static java.lang.String.format;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import com.shop.kinitic.entity.OfferDetails;

public class OfferView {

    private static final String KINITIC_BASE_OFFER_URL = "http://localhost:8080/kinitic-shop/currencies/%d/offers/%d";

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private final Long id;
    private final String name;
    private final String category;
    private final String startDate;
    private final String endDate;
    private final BigDecimal price;
    private final String link;

    public OfferView(final OfferDetails offer, final Long currencyId) {
        this.id = offer.getId();
        this.name = offer.getName();
        this.category = offer.getCategory();
        this.startDate = offer.getStartDate().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
        this.endDate = offer.getExpiryDate().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
        this.price = offer.getPrice();
        this.link = format(KINITIC_BASE_OFFER_URL, currencyId, offer.getId());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getLink() {
        return link;
    }
}

