/**
 *
 */
package com.vnm.creditcardprocessor.repository;

import com.vnm.creditcardprocessor.model.CCTransactionRequestModel;
import com.vnm.creditcardprocessor.model.CreditCardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author  varmathu0
 * @project Credit-Card-Processor
 */

@Repository
public class CreditCardProcessorDAOImpl implements CreditCardProcessorDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<Map<String, Object>> getAllCards() throws DataAccessException {
        return jdbcTemplate.queryForList("SELECT * FROM creditcardinfo");
    }

    public int addCard(CreditCardData creditCardData) throws DuplicateKeyException, DataAccessException {
        return jdbcTemplate.update("insert into creditcardinfo (ccnumber,fname,lname,credit_amount,charge_amount,credit_limit) values (?,?,?,?,?,?)", new Object[]{
                creditCardData.getCardNumber(), creditCardData.getFirstName(), creditCardData.getLastName(),
                creditCardData.getTotalCredit(), creditCardData.getTotalCharge(), creditCardData.getCreditLimit()
        });
    }

    public List<Map<String, Object>> getCardbyNumber(CCTransactionRequestModel CCTransactionRequestModel) throws DataAccessException {
        return jdbcTemplate.queryForList("SELECT * FROM creditcardinfo WHERE ccnumber = ?",
                CCTransactionRequestModel.getCardNumber());
    }

    public int updateCard(CreditCardData creditCardData) throws DataAccessException {
        return jdbcTemplate.update("UPDATE creditcardinfo SET credit_amount=?, charge_amount=? WHERE ccnumber=? "
                , creditCardData.getTotalCredit(), creditCardData.getTotalCharge(), creditCardData.getCardNumber());
    }
}
