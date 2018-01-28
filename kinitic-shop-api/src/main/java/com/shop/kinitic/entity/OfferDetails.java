package com.shop.kinitic.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OfferDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String category;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private BigDecimal price;

    @JsonIgnore
    @ManyToOne
    private Currency currency;

    public OfferDetails() { // for JPA
    }

    public OfferDetails(final Currency currency, final String name, final String category, final LocalDate startDate, final LocalDate expiryDate, final BigDecimal price) {
        this.currency = currency;
        this.name = name;
        this.category = category;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.price = price.setScale(2, RoundingMode.CEILING);

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
