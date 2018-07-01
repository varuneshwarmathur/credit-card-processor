/**
 *
 */
package com.vnm.creditcardprocessor.controller;

import com.vnm.creditcardprocessor.constants.CreditCardDataConstants;
import com.vnm.creditcardprocessor.model.CCGenericResponse;
import com.vnm.creditcardprocessor.model.CCTransactionRequestModel;
import com.vnm.creditcardprocessor.service.CCProcessorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author  varmathu0
 * @project Credit-Card-Processor
 */

@RunWith(MockitoJUnitRunner.class)
public class CreditCardDataControllerTest {

    @Mock
    CCProcessorService processorService;

    @InjectMocks
    CreditCardDataController creditCardDataController;

    @Test
    public void test_addCard() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4111111111111121");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");

        CCGenericResponse ccGenericResponse = creditCardDataController.addCard(CCTransactionRequestModel);
        assertEquals(CreditCardDataConstants.CARD_NUMBER_ERROR, ccGenericResponse.getErrors().getMessage());
    }

    @Test
    public void test_add() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4111111111111111");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCGenericResponse ccGenericResponse = mock(CCGenericResponse.class);
        when(processorService.addCard(Mockito.any())).thenReturn(ccGenericResponse);
        CCGenericResponse response = creditCardDataController.addCard(CCTransactionRequestModel);
        assertEquals(ccGenericResponse, response);

    }

    @Test
    public void test_charge() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4111111111111111");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCTransactionRequestModel.setTransactionAmount(CreditCardDataConstants.POUND + "20");
        CCGenericResponse ccGenericResponse = mock(CCGenericResponse.class);
        when(processorService.chargeCard(CCTransactionRequestModel)).thenReturn(ccGenericResponse);
        CCGenericResponse response = creditCardDataController.charge(CCTransactionRequestModel);
        assertEquals(ccGenericResponse, response);
    }

    @Test
    public void test_credit() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4111111111111111");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCTransactionRequestModel.setTransactionAmount(CreditCardDataConstants.POUND + "20");
        CCGenericResponse ccGenericResponse = mock(CCGenericResponse.class);
        when(processorService.credit(CCTransactionRequestModel)).thenReturn(ccGenericResponse);
        CCGenericResponse response = creditCardDataController.credit(CCTransactionRequestModel);
        assertEquals(ccGenericResponse, response);
    }

    @Test
    public void test_charge_invalidAmount() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4111111111111111");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCTransactionRequestModel.setTransactionAmount("20");
        CCGenericResponse ccGenericResponse = creditCardDataController.charge(CCTransactionRequestModel);
        assertEquals(CreditCardDataConstants.INPUT_FORMAT_ERROR, ccGenericResponse.getErrors().getMessage());
    }

    @Test
    public void test_credit_invalidAmount() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4111111111111111");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCTransactionRequestModel.setTransactionAmount("20");
        CCGenericResponse ccGenericResponse = creditCardDataController.credit(CCTransactionRequestModel);
        assertEquals(CreditCardDataConstants.INPUT_FORMAT_ERROR, ccGenericResponse.getErrors().getMessage());
    }
}
