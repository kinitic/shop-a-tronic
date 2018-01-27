package com.shop.kinitic;

import static java.util.Arrays.asList;

import java.util.Arrays;

import com.shop.kinitic.entity.Currency;
import com.shop.kinitic.repository.CurrencyRepository;
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
    CommandLineRunner init(CurrencyRepository currencyRepository) {
        // TODO: read from some cvs file.
        final Currency gbpCurrency = new Currency("GBP", "Pounds Sterling");
        final Currency usdCurrency = new Currency("USD", "US Dollars");

        return (evt) -> asList(gbpCurrency, usdCurrency).forEach(
                currency -> currencyRepository.save(currency)
        );
    }
}
