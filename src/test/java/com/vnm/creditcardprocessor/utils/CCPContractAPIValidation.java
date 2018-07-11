package com.vnm.creditcardprocessor.utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import com.atlassian.oai.validator.restassured.SwaggerValidationFilter;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;


/**

/**
 * @author myname varmathu0
 * @project Credit-Card-Processor
 */


 /*
 * An example that uses the {@link SwaggerValidationFilter} to validate request/response interactions
 * mediated by the REST-Assured library against a Swagger API specification.
 * <p>
 * The filter can be applied to any REST-Assured given-when-then interaction and allows developers to test
 * that their REST service implementation matches their API specification. This is particularly useful when using
 * a design-first approach where the implementation is separate from the specification. However, even in cases where
 * the specification is generated from the implementation this can yield benefits, as a lot of the information in
 * the specification comes from metadata applied to the implementation (e.g. via annotations on the resource methods)
 * which are not checked at compile time.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CCPContractAPIValidation.class)

public class CCPContractAPIValidation {

    private static final String SWAGGER_JSON_URL = "/Users/varmathu0/Desktop/Code/CreditCardProcessor/credit-card-processor/src/main/resources/swagger_ccp.yaml";
    //private static final int PORT = 9999;

    private final SwaggerValidationFilter validationFilter = new SwaggerValidationFilter(SWAGGER_JSON_URL);

    // Using wiremock to simulate a production service.
    // In a real-world use case you would call out to your service (e.g. in a Spring WebMVC test,
    // or to a service running in your TEST environment etc.)



    /**
     * Test a GET with a valid request/response
     * <p>
     * This test is expected to PASS
     */
    @Test
    public void testGetValidPet() {
        given()

                .filter(validationFilter)
                .when()
                .get("http://localhost:8080/ccp/v1/list-cards")
                .then()
                .assertThat()
                .statusCode(200);
    }

//    /**
//     * Test a GET with an invalid request/response expectation.
//     * <p>
//     * This test will pass the business logic tests, but the validation filter will fail the test because the
//     * response received from the server doesn't match the schema defined in the API specification.
//     * <p>
//     * This could be due to a bug in the implementation, or a problem in the API specification.
//     * Regardless - something is wrong and should be addressed.
//     * <p>
//     * This test is expected to FAIL
//     */
//    @Test
//    public void testGetInvalidPet() {
//        given()
//                .port(PORT)
//                .filter(validationFilter)
//                .when()
//                .get("/pet/2")
//                .then()
//                .assertThat()
//                .statusCode(200);
//    }
//
//    /**
//     * Test a GET with an invalid request/response expectation.
//     * <p>
//     * This test will pass the business logic tests, but the validation filter will fail the test because even though
//     * the server returned a valid result the request used a path parameter that does not match the schema defined
//     * in the API specification.
//     * <p>
//     * This could be due to a bug in the implementation, or a problem in the API specification.
//     * Regardless - something is wrong and should be addressed.
//     * <p>
//     * This test is expected to PASS
//     */
//    @Test
//    public void testGetWithInvalidId() {
//        given()
//                .port(PORT)
//                .filter(validationFilter)
//                .when()
//                .get("/pet/fido")
//                .then()
//                .assertThat()
//                .statusCode(200);
//    }

}
