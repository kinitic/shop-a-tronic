package com.shop.kinitic.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Offer {
    
    private final String name;
    private final String category;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final BigDecimal price;

    public Offer(final String name, final String category, final LocalDate startDate, final LocalDate endDate, final BigDecimal price) {
        this.name = name;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
