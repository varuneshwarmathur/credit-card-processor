package com.vnm.creditcardprocessor.service;

import com.vnm.creditcardprocessor.model.CCGenericResponse;
import com.vnm.creditcardprocessor.model.CCTransactionRequestModel;
import com.vnm.creditcardprocessor.model.CreditCardData;
import com.vnm.creditcardprocessor.model.CreditCardListResponse;

/**
 * @author  varmathu0
 * @project Credit-Card-Processor
 */

public interface CCProcessorService {

    public CCGenericResponse addCard(CreditCardData creditCardData);

    public CreditCardListResponse getAllCards();

    public CCGenericResponse chargeCard(CCTransactionRequestModel CCTransactionRequestModel);

    public CCGenericResponse credit(CCTransactionRequestModel CCTransactionRequestModel);

}
