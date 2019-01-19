
package com.adidas.subscription.repository;

import com.adidas.subscription.model.SubscriptionServiceData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author  varmathu0
 */


public interface SubscriptionServiceDAO extends CrudRepository<SubscriptionServiceData, String> {


    SubscriptionServiceData findByEmailId(String emailId);
}