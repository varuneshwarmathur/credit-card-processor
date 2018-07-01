/**
 *
 */
package com.vnm.creditcardprocessor.repository;

import com.vnm.creditcardprocessor.model.CCTransactionRequestModel;
import com.vnm.creditcardprocessor.model.CreditCardData;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Map;

/**
 * @author  varmathu0
 * @project Credit-Card-Processor
 */


public interface CreditCardProcessorDAO {

    public List<Map<String, Object>> getAllCards() throws DataAccessException;

    public List<Map<String, Object>> getCardbyNumber(CCTransactionRequestModel CCTransactionRequestModel) throws DataAccessException;

    public int addCard(CreditCardData creditCardData) throws DuplicateKeyException, DataAccessException;

    public int updateCard(CreditCardData creditCardData) throws DataAccessException;

}
