package com.adidas.subscription.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author  varmathu0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionServiceResponse {

    private String subscriptionId;
    private SubscriptionServiceError error;
    private String email;
    private String firstName;
    private Boolean consentGranted;
    private String dateOfBirth;
    private Object eventServiceResponse;
    private Object emailServiceResposne;

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public SubscriptionServiceError getError() {
        return error;
    }

    public void setError(SubscriptionServiceError error) {
        this.error = error;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getConsentGranted() {
        return consentGranted;
    }

    public void setConsentGranted(Boolean consentGranted) {
        this.consentGranted = consentGranted;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Object getEventServiceResponse() {
        return eventServiceResponse;
    }

    public void setEventServiceResponse(Object eventServiceResponse) {
        this.eventServiceResponse = eventServiceResponse;
    }

    public Object getEmailServiceResposne() {
        return emailServiceResposne;
    }

    public void setEmailServiceResposne(Object emailServiceResposne) {
        this.emailServiceResposne = emailServiceResposne;
    }
}
