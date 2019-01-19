package com.adidas.subscription.service;

import com.adidas.subscription.model.SubscriptionServiceResponse;

import com.adidas.subscription.model.SubscriptionServiceData;


/**
 * @author  varmathu0
 */


public interface SubscriptionProcessorService {

    public SubscriptionServiceResponse createSubscription(SubscriptionServiceData subscriptionServiceData);
}
