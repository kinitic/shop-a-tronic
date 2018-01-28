package com.shop.kinitic.views;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OfferView {
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private final String name;
    private final String category;
    private final String startDate;
    private final String endDate;
    private final BigDecimal price;

    public OfferView(final String name, final String category, final LocalDate startDate, final LocalDate endDate, final BigDecimal price) {
        this.name = name;
        this.category = category;
        this.startDate = startDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
        this.endDate = endDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
        this.price = price;
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
}

