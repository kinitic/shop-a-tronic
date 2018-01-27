package com.shop.kinitic.resources;

import com.shop.kinitic.views.CurrencyView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kinitic-shop")
public class CurrencyResource {

    @RequestMapping(method = RequestMethod.GET, path = "currencies")
    public CurrencyView getCurrencies() {
        return new CurrencyView();
    }
}
