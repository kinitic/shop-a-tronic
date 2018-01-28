package com.shop.kinitic.repository;

import java.util.List;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.entity.OfferDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<OfferDetails, Long> {
    List<OfferDetails> findByCurrency(final Currency currency);

}
