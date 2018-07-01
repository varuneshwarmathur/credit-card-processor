/**
 *
 */
package com.vnm.creditcardprocessor.utils;

import com.vnm.creditcardprocessor.constants.CreditCardDataConstants;
import com.vnm.creditcardprocessor.model.CCTransactionRequestModel;
import com.vnm.creditcardprocessor.model.CreditCardData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author  varmathu0
 * @project Credit-Card-Processor
 */

@RunWith(MockitoJUnitRunner.class)
public class CCProcessorUtilsTest {

    @Test
    public void test_validateCardDetails() {
        CreditCardData creditCardData = mock(CreditCardData.class);
        when(creditCardData.getCardNumber()).thenReturn("4111111111111111");
        assertEquals(true, CCProcessorUtils.validateCardDetails(creditCardData));
    }

    @Test
    public void test_validateCardDetails_AlphabeticCard() {
        CreditCardData creditCardData = mock(CreditCardData.class);
        when(creditCardData.getCardNumber()).thenReturn("abcd3243");
        assertEquals(false, CCProcessorUtils.validateCardDetails(creditCardData));
    }

    @Test
    public void test_validateCardDetails_LengthFail() {
        CreditCardData creditCardData = mock(CreditCardData.class);
        when(creditCardData.getCardNumber()).thenReturn("4111111111111111111122");
        assertEquals(false, CCProcessorUtils.validateCardDetails(creditCardData));
    }

    @Test
    public void test_validateCardDetails_negativeCard() {
        CreditCardData creditCardData = mock(CreditCardData.class);
        when(creditCardData.getCardNumber()).thenReturn("-4111111111111111");
        assertEquals(false, CCProcessorUtils.validateCardDetails(creditCardData));
    }

    @Test
    public void test_validateChargeDetails() {
        CCTransactionRequestModel CCTransactionRequestModel = mock(CCTransactionRequestModel.class);
        when(CCTransactionRequestModel.getTransactionAmount()).thenReturn(CreditCardDataConstants.POUND + "20");
        assertEquals(true, CCProcessorUtils.validateChargeDetails(CCTransactionRequestModel));
    }

    @Test
    public void test_validateChargeDetails_withoutPound() {
        CCTransactionRequestModel CCTransactionRequestModel = mock(CCTransactionRequestModel.class);
        when(CCTransactionRequestModel.getTransactionAmount()).thenReturn("20");
        assertEquals(false, CCProcessorUtils.validateChargeDetails(CCTransactionRequestModel));
    }

    @Test
    public void test_validateChargeDetails_negative() {
        CCTransactionRequestModel CCTransactionRequestModel = mock(CCTransactionRequestModel.class);
        when(CCTransactionRequestModel.getTransactionAmount()).thenReturn("-20");
        assertEquals(false, CCProcessorUtils.validateChargeDetails(CCTransactionRequestModel));
    }

    @Test
    public void test_validateChargeDetails_numberFormat() {
        CCTransactionRequestModel CCTransactionRequestModel = mock(CCTransactionRequestModel.class);
        when(CCTransactionRequestModel.getTransactionAmount()).thenReturn("20sf");
        assertEquals(false, CCProcessorUtils.validateChargeDetails(CCTransactionRequestModel));
    }

    @Test
    public void test_validateChargeDetails_nullCharge() {
        CCTransactionRequestModel CCTransactionRequestModel = mock(CCTransactionRequestModel.class);
        when(CCTransactionRequestModel.getTransactionAmount()).thenReturn(null);
        assertEquals(false, CCProcessorUtils.validateChargeDetails(CCTransactionRequestModel));
    }

    @Test
    public void test_formatAmount() {
        String amount = "20";
        assertEquals(CreditCardDataConstants.POUND + "20.00", CCProcessorUtils.formatAmount(amount));
    }

    @Test
    public void test_formatAmount_negative() {
        String amount = "-20";
        assertEquals(CreditCardDataConstants.NEGATIVE_POUND + "20.00", CCProcessorUtils.formatAmount(amount));
    }
}
