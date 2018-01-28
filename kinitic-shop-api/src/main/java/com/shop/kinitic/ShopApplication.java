package com.shop.kinitic;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.entity.OfferDetails;
import com.shop.kinitic.repository.CurrencyRepository;
import com.shop.kinitic.repository.OfferRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Bean
    CommandLineRunner init(final CurrencyRepository currencyRepository, final OfferRepository offerRepository) {
        // TODO: read from some cvs file.
        final Currency gbpCurrency = new Currency("GBP", "Pounds Sterling");
        final Currency usdCurrency = new Currency("USD", "US Dollars");
        currencyRepository.save(gbpCurrency);
        currencyRepository.save(usdCurrency);

        final OfferDetails toys = new OfferDetails(gbpCurrency, "Cuddly Toy", "Toys", LocalDate.of(2017, 12, 10), LocalDate.of(2020, 12, 10), BigDecimal.valueOf(10.99));
        final OfferDetails electricals = new OfferDetails(gbpCurrency, "Toaster", "Electricals", LocalDate.of(2018, 1, 26), LocalDate.of(2020, 1, 26), BigDecimal.valueOf(12.95));
        final OfferDetails books = new OfferDetails(gbpCurrency, "Effective Java", "Books", LocalDate.of(2016, 1, 10), LocalDate.of(2020, 12, 22), BigDecimal.valueOf(29.99));

        return (evt) -> asList(toys, electricals, books).forEach(
                offer -> offerRepository.save(offer)
        );
    }
}
