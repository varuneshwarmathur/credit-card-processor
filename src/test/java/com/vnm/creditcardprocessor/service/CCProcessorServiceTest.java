/**
 *
 */
package com.vnm.creditcardprocessor.service;

import com.vnm.creditcardprocessor.constants.CreditCardDataConstants;
import com.vnm.creditcardprocessor.model.CCGenericResponse;
import com.vnm.creditcardprocessor.model.CCTransactionRequestModel;
import com.vnm.creditcardprocessor.model.CreditCardData;
import com.vnm.creditcardprocessor.model.CreditCardListResponse;
import com.vnm.creditcardprocessor.repository.CreditCardProcessorDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author  varmathu0
 * @project Credit-Card-Processor
 */


@RunWith(MockitoJUnitRunner.class)
public class CCProcessorServiceTest {

    @Mock
    private CreditCardProcessorDAO creditCardProcessorDAO;

    @InjectMocks
    private CCProcessorServiceImpl ccProcessServiceImpl;

    @Test
    public void test_addCard() {
        CreditCardData creditCardData = mock(CreditCardData.class);
        when(creditCardData.getCardNumber()).thenReturn("4111111111111111");
        CCGenericResponse ccGenericResponse = ccProcessServiceImpl.addCard(creditCardData);
        assertNotNull(ccGenericResponse);
    }

    @Test
    public void test_addCard_duplicate() {
        CreditCardData creditCardData = mock(CreditCardData.class);
        when(creditCardData.getCardNumber()).thenReturn("5105105105105100");
        when(creditCardProcessorDAO.addCard(creditCardData)).thenThrow(DuplicateKeyException.class);
        CCGenericResponse ccGenericResponse = ccProcessServiceImpl.addCard(creditCardData);
        assertEquals(CreditCardDataConstants.CARD_ALREADY_EXIST, ccGenericResponse.getErrors().getMessage());
    }

    @Test
    public void test_getAllCards() {
        CreditCardListResponse response = ccProcessServiceImpl.getAllCards();
        assertNull(response.getErrors());
    }

    @Test
    public void test_chargeCard() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4111111111111111");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCTransactionRequestModel.setTransactionAmount("20");

        List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("ccnumber", "4111111111111111");
        cardMap.put("fname", "varuneshwar");
        cardMap.put("lname", "mathur");
        cardMap.put("credit_amount", "70");
        cardMap.put("charge_amount", "100");
        cardMap.put("credit_limit", "1000");
        cardList.add(cardMap);
        when(creditCardProcessorDAO.getCardbyNumber(CCTransactionRequestModel)).thenReturn(cardList);

        CCGenericResponse ccGenericResponse = ccProcessServiceImpl.chargeCard(CCTransactionRequestModel);
        assertEquals(CreditCardDataConstants.POUND + "50.00", ccGenericResponse.getBalanceRemaining());
    }

    @Test
    public void test_chargeCard_cardNotFound() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("123213123");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCTransactionRequestModel.setTransactionAmount("20");
        CCGenericResponse ccGenericResponse = ccProcessServiceImpl.chargeCard(CCTransactionRequestModel);
        assertEquals(CreditCardDataConstants.CARD_NOT_FOUND, ccGenericResponse.getErrors().getMessage());
    }

    @Test
    public void test_chargeCard_limit() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4111111111111111");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCTransactionRequestModel.setTransactionAmount("120");

        List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("ccnumber", "4111111111111111");
        cardMap.put("fname", "varuneshwar");
        cardMap.put("lname", "mathur");
        cardMap.put("credit_amount", "0");
        cardMap.put("charge_amount", "900");
        cardMap.put("credit_limit", "1000");
        cardList.add(cardMap);
        when(creditCardProcessorDAO.getCardbyNumber(CCTransactionRequestModel)).thenReturn(cardList);

        CCGenericResponse ccGenericResponse = ccProcessServiceImpl.chargeCard(CCTransactionRequestModel);
        assertEquals(CreditCardDataConstants.LIMIT_EXCEED_ERROR, ccGenericResponse.getErrors().getMessage());
    }

    @Test
    public void test_chargeCard_GenericError() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4111111111111111");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCTransactionRequestModel.setTransactionAmount("abcde40");

        List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("ccnumber", "4111111111111111");
        cardMap.put("fname", "varuneshwar");
        cardMap.put("lname", "mathur");
        cardMap.put("credit_amount", "70");
        cardMap.put("charge_amount", "100");
        cardMap.put("credit_limit", "1000");
        cardList.add(cardMap);
        when(creditCardProcessorDAO.getCardbyNumber(CCTransactionRequestModel)).thenReturn(cardList);

        CCGenericResponse ccGenericResponse = ccProcessServiceImpl.chargeCard(CCTransactionRequestModel);
        assertEquals(CreditCardDataConstants.GENERIC_ERROR_MESSAGE, ccGenericResponse.getErrors().getMessage());
    }

    @Test
    public void test_credit() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4111111111111111");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCTransactionRequestModel.setTransactionAmount("20");

        List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("ccnumber", "4111111111111111");
        cardMap.put("fname", "varuneshwar");
        cardMap.put("lname", "mathur");
        cardMap.put("credit_amount", "70");
        cardMap.put("charge_amount", "100");
        cardMap.put("credit_limit", "1000");
        cardList.add(cardMap);
        when(creditCardProcessorDAO.getCardbyNumber(CCTransactionRequestModel)).thenReturn(cardList);

        CCGenericResponse ccGenericResponse = ccProcessServiceImpl.credit(CCTransactionRequestModel);
        assertEquals(CreditCardDataConstants.POUND + "10.00", ccGenericResponse.getBalanceRemaining());
    }

    @Test
    public void test_credit_cardNotFound() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4012888888881881");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCTransactionRequestModel.setTransactionAmount("20");
        CCGenericResponse ccGenericResponse = ccProcessServiceImpl.credit(CCTransactionRequestModel);
        assertEquals(CreditCardDataConstants.CARD_NOT_FOUND, ccGenericResponse.getErrors().getMessage());
    }

    @Test
    public void test_credit_genericErrro() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4111111111111111");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCTransactionRequestModel.setTransactionAmount("20nn");

        List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("ccnumber", "4111111111111111");
        cardMap.put("fname", "varuneshwar");
        cardMap.put("lname", "mathur");
        cardMap.put("credit_amount", "70");
        cardMap.put("charge_amount", "100");
        cardMap.put("credit_limit", "1000");
        cardList.add(cardMap);
        when(creditCardProcessorDAO.getCardbyNumber(CCTransactionRequestModel)).thenReturn(cardList);

        CCGenericResponse ccGenericResponse = ccProcessServiceImpl.credit(CCTransactionRequestModel);
        assertEquals(CreditCardDataConstants.GENERIC_ERROR_MESSAGE, ccGenericResponse.getErrors().getMessage());
    }

    @Test
    public void test_getExistingCardDetails() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4111111111111111");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCTransactionRequestModel.setTransactionAmount("20");

        List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("ccnumber", "4111111111111111");
        cardMap.put("fname", "varuneshwar");
        cardMap.put("lname", "mathur");
        cardMap.put("credit_amount", "70");
        cardMap.put("charge_amount", "100");
        cardMap.put("credit_limit", "1000");;
        cardList.add(cardMap);
        when(creditCardProcessorDAO.getCardbyNumber(CCTransactionRequestModel)).thenReturn(cardList);

        CreditCardData cardInfo = ccProcessServiceImpl.getExistingCardDetails(CCTransactionRequestModel);
        assertEquals("varuneshwar", cardInfo.getFirstName());
    }

    @Test
    public void test_getExistingCardDetails_NoCard() {
        CCTransactionRequestModel CCTransactionRequestModel = new CCTransactionRequestModel();
        CCTransactionRequestModel.setCardNumber("4111111111111111");
        CCTransactionRequestModel.setFirstName("varuneshwar");
        CCTransactionRequestModel.setLastName("mathur");
        CCTransactionRequestModel.setTransactionAmount("20");

        List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();

        when(creditCardProcessorDAO.getCardbyNumber(CCTransactionRequestModel)).thenReturn(cardList);

        CreditCardData cardInfo = ccProcessServiceImpl.getExistingCardDetails(CCTransactionRequestModel);
        assertEquals("", cardInfo.getFirstName());
    }

}
