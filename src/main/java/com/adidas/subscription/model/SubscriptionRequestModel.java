/**
 *
 */
package com.adidas.subscription.model;

import com.adidas.subscription.constants.SubscriptionServiceConstants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author  varmathu0
 */

public class SubscriptionRequestModel {

    private String firstName;
    private String gender;
    @NotNull(message = SubscriptionServiceConstants.EMAIL_MISSING)
    @NotEmpty(message = SubscriptionServiceConstants.EMAIL_EMPTY)
    private String emailId;
    @NotNull(message = SubscriptionServiceConstants.DOB_MISSING)
    @NotEmpty(message = SubscriptionServiceConstants.DOB_EMPTY)
    private String dateOfBirth;
    @NotNull(message = SubscriptionServiceConstants.CONSENT_MISSING)
    private Boolean consentGranted;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getConsentGranted() {
        return consentGranted;
    }

    public void setConsentGranted(Boolean consentGranted) {
        this.consentGranted = consentGranted;
    }
}
