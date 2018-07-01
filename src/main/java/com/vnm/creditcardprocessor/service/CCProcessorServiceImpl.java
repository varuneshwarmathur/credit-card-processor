package com.vnm.creditcardprocessor.service;

import com.vnm.creditcardprocessor.constants.CreditCardDataConstants;
import com.vnm.creditcardprocessor.model.*;
import com.vnm.creditcardprocessor.repository.CreditCardProcessorDAO;
import com.vnm.creditcardprocessor.utils.CCProcessorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author  varmathu0
 * @project Credit-Card-Processor
 */

@Service
public class CCProcessorServiceImpl implements CCProcessorService {

    @Autowired
    private CreditCardProcessorDAO creditCardProcessorDAO;

    @Override
    public CCGenericResponse addCard(CreditCardData creditCardData) {
        CCGenericResponse ccGenericResponse = new CCGenericResponse();
        ccGenericResponse.setCardNumber(creditCardData.getCardNumber());
        ccGenericResponse.setBalanceRemaining("0");

        try {
            int response = creditCardProcessorDAO.addCard(creditCardData);
        } catch (DuplicateKeyException e) {
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.CARD_ALREADY_EXIST);
            ccGenericResponse.setErrors(errors);
        } catch (DataAccessException e) {
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.GENERIC_ERROR_MESSAGE);
            ccGenericResponse.setErrors(errors);
        }

        return ccGenericResponse;

    }

    public CreditCardListResponse getAllCards() {
        CreditCardListResponse cardList = new CreditCardListResponse();
        List<CreditCardData> creditCards = new ArrayList<CreditCardData>();
        try {
            List<Map<String, Object>> cards = creditCardProcessorDAO.getAllCards();
            for (Map<String, Object> row : cards) {
                CreditCardData cardData = new CreditCardData();
                cardData.setCardNumber((String) row.get("ccnumber"));
                cardData.setFirstName((String) row.get("fname"));
                cardData.setLastName((String) row.get("lname"));
                cardData.setTotalCredit(CCProcessorUtils.formatAmount((String) row.get("credit_amount")));
                cardData.setTotalCharge(CCProcessorUtils.formatAmount((String) row.get("charge_amount")));
                cardData.setCreditLimit(CCProcessorUtils.formatAmount((String) row.get("credit_limit")));
                creditCards.add(cardData);
            }
        } catch (DataAccessException | NumberFormatException e) {
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.GENERIC_ERROR_MESSAGE);
            cardList.setErrors(errors);
        }

        if (!creditCards.isEmpty()) {
            cardList.setCreditCards(creditCards);
        }
        return cardList;
    }

    @Override
    public CCGenericResponse chargeCard(CCTransactionRequestModel CCTransactionRequestModel) {
        CCGenericResponse ccGenericResponse = new CCGenericResponse();

        try {
            CreditCardData cardInfo = getExistingCardDetails(CCTransactionRequestModel);
            if (null == cardInfo.getCardNumber() || cardInfo.getCardNumber().isEmpty()) {
                ccGenericResponse.setCardNumber(CCTransactionRequestModel.getCardNumber());
                CCError errors = new CCError();
                errors.setMessage(CreditCardDataConstants.CARD_NOT_FOUND);
                ccGenericResponse.setErrors(errors);
            } else {
                double transactionAmount = Double.parseDouble(CCTransactionRequestModel.getTransactionAmount());
                //double balance = Double.parseDouble(cardInfo.getBalanceRemaining());
                double limit = Double.parseDouble(cardInfo.getCreditLimit());
                double currentCharge = Double.parseDouble(cardInfo.getTotalCharge());
                double currentCredit = Double.parseDouble(cardInfo.getTotalCredit());

                double balance = ((currentCharge + transactionAmount) - currentCredit);
                System.err.println("transactionAmount : " + transactionAmount + "|| currentCharge : " + currentCharge + " || currentCredit : " + currentCredit);

                System.err.println("balance  : " + ((currentCharge + transactionAmount) - currentCredit));

                if (balance > limit) {
                    ccGenericResponse.setCardNumber(CCTransactionRequestModel.getCardNumber());
                    ccGenericResponse.setBalanceRemaining(CCProcessorUtils.formatAmount(String.valueOf(balance)));
                    CCError errors = new CCError();
                    errors.setMessage(CreditCardDataConstants.LIMIT_EXCEED_ERROR);
                    ccGenericResponse.setErrors(errors);
                } else {
                    cardInfo.setTotalCharge(String.valueOf(transactionAmount + currentCharge));
                    creditCardProcessorDAO.updateCard(cardInfo);
                    ccGenericResponse.setCardNumber(cardInfo.getCardNumber());
                    ccGenericResponse.setTotalCharge(cardInfo.getTotalCharge());
                    ccGenericResponse.setTotalCredit(cardInfo.getTotalCredit());
                    ccGenericResponse.setBalanceRemaining(CCProcessorUtils.formatAmount(String.valueOf(balance)));

                }
            }
        } catch (DataAccessException | NumberFormatException e) {
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.GENERIC_ERROR_MESSAGE);
            ccGenericResponse.setErrors(errors);
        }

        return ccGenericResponse;
    }

    @Override
    public CCGenericResponse credit(CCTransactionRequestModel CCTransactionRequestModel) {
        CCGenericResponse ccGenericResponse = new CCGenericResponse();
        try {
            CreditCardData cardInfo = getExistingCardDetails(CCTransactionRequestModel);

            if (null == cardInfo.getCardNumber() || cardInfo.getCardNumber().isEmpty()) {
                ccGenericResponse.setCardNumber(CCTransactionRequestModel.getCardNumber());
                CCError errors = new CCError();
                errors.setMessage(CreditCardDataConstants.CARD_NOT_FOUND);
                ccGenericResponse.setErrors(errors);
            } else {
                double transactionAmount = Double.parseDouble(CCTransactionRequestModel.getTransactionAmount());
                double currentCharge = Double.parseDouble(cardInfo.getTotalCharge());
                double currentCredit = Double.parseDouble(cardInfo.getTotalCredit());

                double balance = ((currentCharge) - (currentCredit + transactionAmount));

                System.err.println("transactionAmount : " + transactionAmount + "|| currentCharge : " + currentCharge + "|| currentCredit : " + currentCredit);
                System.err.println("balance  : " + ((currentCharge) - (currentCredit + transactionAmount)));

                cardInfo.setTotalCredit(String.valueOf(currentCredit + transactionAmount));
                creditCardProcessorDAO.updateCard(cardInfo);
                ccGenericResponse.setCardNumber(cardInfo.getCardNumber());
                ccGenericResponse.setCardNumber(cardInfo.getCardNumber());
                ccGenericResponse.setTotalCharge(cardInfo.getTotalCharge());
                ccGenericResponse.setTotalCredit(cardInfo.getTotalCredit());
                ccGenericResponse.setBalanceRemaining(CCProcessorUtils.formatAmount(String.valueOf(balance)));

            }
        } catch (DataAccessException | NumberFormatException e) {
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.GENERIC_ERROR_MESSAGE);
            ccGenericResponse.setErrors(errors);
        }

        return ccGenericResponse;
    }

    public CreditCardData getExistingCardDetails(CCTransactionRequestModel CCTransactionRequestModel) throws DataAccessException, NumberFormatException {

        List<Map<String, Object>> cards = creditCardProcessorDAO.getCardbyNumber(CCTransactionRequestModel);

        if (cards.isEmpty()) {
            return new CreditCardData();
        }
        CreditCardData cardInfo = new CreditCardData();
        for (Map<String, Object> row : cards) {
            cardInfo.setCardNumber((String) row.get("ccnumber"));
            cardInfo.setFirstName((String) row.get("fname"));
            cardInfo.setLastName((String) row.get("lname"));
            cardInfo.setCreditLimit((String) row.get("credit_limit"));
            cardInfo.setTotalCredit((String) row.get("credit_amount"));
            cardInfo.setTotalCharge((String) row.get("charge_amount"));
        }

        return cardInfo;
    }
}
