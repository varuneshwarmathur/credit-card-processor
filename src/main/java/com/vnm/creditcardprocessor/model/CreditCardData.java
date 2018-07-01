/**
 *
 */
package com.vnm.creditcardprocessor.model;

/**
 * @author  varmathu0
 * @project Credit-Card-Processor
 */
public class CreditCardData {

    private String cardNumber;

    private String firstName;

    private String lastName;

    private String totalCredit;

    private String totalCharge;

    private String creditLimit;

    public CreditCardData() {
        this.cardNumber = "";
        this.firstName = "";
        this.lastName = "";

    }

    public CreditCardData(String cardNumber) {
        this.cardNumber = cardNumber;
        this.firstName = "";
        this.lastName = "";

    }

    public CreditCardData(CCTransactionRequestModel requestModel) {
        this.cardNumber = requestModel.getCardNumber();
        this.firstName = requestModel.getFirstName();
        this.lastName = requestModel.getLastName();
        this.creditLimit = requestModel.getCreditLimit();
        this.totalCharge = requestModel.getChargeAmount();
        this.totalCredit = requestModel.getCreditAmount();


    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(String totalCharge) {
        this.totalCharge = totalCharge;
    }

}
