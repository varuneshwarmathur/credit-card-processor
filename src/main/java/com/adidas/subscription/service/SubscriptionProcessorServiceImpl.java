package com.adidas.subscription.service;


import com.adidas.subscription.constants.SubscriptionServiceConstants;
import com.adidas.subscription.model.*;
import com.adidas.subscription.repository.SubscriptionServiceDAO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


/**
 * @author  varmathu0
 */

@Service
public class SubscriptionProcessorServiceImpl implements SubscriptionProcessorService {

    @Autowired
    private SubscriptionServiceDAO subscriptionServiceDAO;

    HttpHeaders createHeaders(){
        return new HttpHeaders() {{
            String authHeader = "Basic bWF0aHVyOnBhc3N3b3Jk";
            set( "Authorization", authHeader );
        }};
    }


    final String eventServiceUri =  "http://localhost:9090/adidas/v1/event";
    final String emailServiceUri =  "http://localhost:9091/adidas/v1/send-email";

    @Override
    @HystrixCommand(fallbackMethod = "failed")
    public SubscriptionServiceResponse createSubscription(SubscriptionServiceData subscriptionServiceData) {
        SubscriptionServiceResponse subscriptionServiceResponse = new SubscriptionServiceResponse();
        try {
            RestTemplate restTemplate = new RestTemplate();
            subscriptionServiceDAO.save(subscriptionServiceData);
            subscriptionServiceResponse.setSubscriptionId(subscriptionServiceData.getId());

            subscriptionServiceResponse.setEmailServiceResposne(restTemplate.exchange(emailServiceUri,HttpMethod.POST, new HttpEntity<SubscriptionServiceResponse>(createHeaders()) ,SubscriptionServiceResponse.class ));
            subscriptionServiceResponse.setEventServiceResponse(restTemplate.exchange(eventServiceUri, HttpMethod.POST, new HttpEntity<SubscriptionServiceResponse>(createHeaders()), SubscriptionServiceResponse.class));
        } catch (DataAccessException e) {
            SubscriptionServiceError errors = new SubscriptionServiceError();
            errors.setMessage(SubscriptionServiceConstants.GENERIC_ERROR);
            subscriptionServiceResponse.setError(errors);
        }

        return subscriptionServiceResponse;
    }

    public SubscriptionServiceResponse failed(SubscriptionServiceData subscriptionServiceData){
        SubscriptionServiceError errors = new SubscriptionServiceError();
        SubscriptionServiceResponse subscriptionServiceResponse = new SubscriptionServiceResponse();
        errors.setMessage(SubscriptionServiceConstants.DOWNSTREAM_API_ERROR);
        subscriptionServiceResponse.setError(errors);

        return subscriptionServiceResponse;
    }
}
