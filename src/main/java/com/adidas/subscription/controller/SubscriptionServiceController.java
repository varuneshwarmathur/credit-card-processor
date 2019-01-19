package com.adidas.subscription.controller;


import com.adidas.subscription.model.*;

import com.adidas.subscription.service.SubscriptionProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author  varmathu0
 */
@RestController
public class SubscriptionServiceController {

    @Autowired
    private SubscriptionProcessorService subscriptionProcessorService;

    @RequestMapping(path = "/newsletter-subscription", method = RequestMethod.POST)
    public SubscriptionServiceResponse createSubscription(@Valid @RequestBody SubscriptionRequestModel request) {

        SubscriptionServiceData subscriptionServiceData = new SubscriptionServiceData(request);

        return subscriptionProcessorService.createSubscription(subscriptionServiceData);
    }


    @RequestMapping(path = "/service-info", method = RequestMethod.GET , produces = "application/json")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
