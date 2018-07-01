/**
 *
 */
package com.vnm.creditcardprocessor.model;

/**
 * @author  varmathu0
 * @project Credit-Card-Processor
 */
public class CCGenericResponse {

    private String cardNumber;

    private String balanceRemaining;
    private String totalCredit;
    private String totalCharge;
    private CCError errors;

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(String totalCharge) {
        this.totalCharge = totalCharge;
    }

    public CCError getErrors() {
        return errors;
    }

    public void setErrors(CCError errors) {
        this.errors = errors;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBalanceRemaining() {
        return balanceRemaining;
    }

    public void setBalanceRemaining(String balanceRemaining) {
        this.balanceRemaining = balanceRemaining;
    }

}
