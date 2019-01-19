/**
 *
 */
package com.adidas.subscription.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * @author  varmathu0
 */

@Document(collection = "subscriptions")
public class SubscriptionServiceData {

    @Id
    private String id;
    private String firstName;
    private String gender;
    private String emailId;
    private String dateOfBirth;
    private Boolean consentGranted;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public Boolean getConsentGranted() {
        return consentGranted;
    }

    public void setConsentGranted(Boolean consentGranted) {
        this.consentGranted = consentGranted;
    }

    public SubscriptionServiceData(SubscriptionRequestModel requestModel) {
        this.firstName = requestModel.getFirstName();
        this.emailId = requestModel.getEmailId();
        this.gender = requestModel.getGender();
        this.dateOfBirth = requestModel.getDateOfBirth();
        this.consentGranted = requestModel.getConsentGranted();


    }

    public SubscriptionServiceData() {

    }
}
