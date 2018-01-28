package com.shop.kinitic.model;

import java.math.BigDecimal;

public class Offer {
    
    private String name;
    private String category;
    private String startDate;
    private String expiryDate;
    private BigDecimal price;

    public Offer() { // for de/serialisation
    }

    public Offer(final String name, final String category, final String startDate, final String expiryDate, final BigDecimal price) {
        this.name = name;
        this.category = category;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
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

    public String getExpiryDate() {
        return expiryDate;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
