/**
 *
 */
package com.vnm.creditcardprocessor.controller;

import com.microsoft.applicationinsights.telemetry.SeverityLevel;
import com.vnm.creditcardprocessor.constants.CreditCardDataConstants;
import com.vnm.creditcardprocessor.model.*;
import com.vnm.creditcardprocessor.service.CCProcessorService;
import com.vnm.creditcardprocessor.utils.CCProcessorUtils;
import com.vnm.creditcardprocessor.utils.CreditCardValidator;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import com.microsoft.applicationinsights.TelemetryClient;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.Map;

/**
 * @author  varmathu0
 * @project Credit-Card-Processor
 */
@CrossOrigin
@RestController
public class CreditCardDataController {

    @Autowired
    private CCProcessorService processorService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    TelemetryClient telemetryClient;

    @RequestMapping(path = "/add-credit-card-v2", method = RequestMethod.POST)
    @ApiOperation("Add a new credit card")
    public CCGenericResponse addCard(@RequestBody CCTransactionRequestModel request) {

        CreditCardData creditCardData = new CreditCardData(request);
        CCGenericResponse ccGenericResponse = new CCGenericResponse();
        if (CreditCardValidator.luhnCheck(request.getCardNumber()) && CCProcessorUtils.validateCardDetails(creditCardData)) {
            ccGenericResponse = processorService.addCard(creditCardData);
        } else {
            ccGenericResponse.setCardNumber(request.getCardNumber());
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.CARD_NUMBER_ERROR);
            ccGenericResponse.setErrors(errors);
        }

        return ccGenericResponse;
    }

    @RequestMapping(path = "/charge-amount", method = RequestMethod.PUT)
    @ApiOperation("Charge a credit card with £ currency amount")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "request",
                    dataType = "{\n" +
                            "  \"cardNumber\": \"5105105105105100\",\n" +
                            "  \"transactionAmount\": \"£100\"\n" +
                            "}"
            )
    })
    public CCGenericResponse charge(@RequestBody CCTransactionRequestModel request) {
        CCGenericResponse ccGenericResponse = new CCGenericResponse();
        if (CCProcessorUtils.validateChargeDetails(request)) {
            ccGenericResponse = processorService.chargeCard(request);
        } else {
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.INPUT_FORMAT_ERROR);
            ccGenericResponse.setErrors(errors);
        }

        return ccGenericResponse;
    }

    @RequestMapping(path = "/credit-amount", method = RequestMethod.PUT)
    @ApiOperation("Debit a card with £ currency amount")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "request",
                    dataType = "{\n" +
                            "  \"cardNumber\": \"5105105105105100\",\n" +
                            "  \"transactionAmount\": \"£100\"\n" +
                            "}"
            )
    })
    public CCGenericResponse credit(@RequestBody CCTransactionRequestModel request) {
        CCGenericResponse ccGenericResponse = new CCGenericResponse();
        if (CCProcessorUtils.validateChargeDetails(request)) {
            ccGenericResponse = processorService.credit(request);
        } else {
            CCError errors = new CCError();
            errors.setMessage(CreditCardDataConstants.INPUT_FORMAT_ERROR);
            ccGenericResponse.setErrors(errors);
        }

        return ccGenericResponse;
    }

    @RequestMapping(path = "/list-cards", method = RequestMethod.GET , produces = "application/json")
    @ApiOperation("List all cards from the system")
    public CreditCardListResponse getCards(@RequestHeader Map<String, String> headers) {
        System.out.println(headers);
        String ipAddress = request.getRemoteAddr();
        System.out.println(ipAddress);
        telemetryClient.trackEvent("Tracking Request for List Cards");
        telemetryClient.trackTrace("GET /LIST API HIT", SeverityLevel.Information);
        return processorService.getAllCards();

    }

}
