package myrest.steps.serenity;

import myrest.model.Customer;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.apache.http.HttpStatus;

import java.net.URI;
import java.net.URISyntaxException;

import static net.serenitybdd.rest.SerenityRest.rest;
import static org.hamcrest.Matchers.is;

public class RestSteps extends ScenarioSteps {

    public static final String BASE_URL = "http://localhost:8080/sqlrest/";
    public static final String CUSTOMER = "CUSTOMER/";

    @Step
    public void getAllCustomers() {
        URI uri = null;
        try {
            uri = new URI(BASE_URL + CUSTOMER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        rest().
                when().get(uri).
                then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Step
    public void getCustomerWithID(int id) {
        rest().
                when().get(BASE_URL + CUSTOMER + "{+" + id + "+}", id).
                then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Step
    public void getNonExistingCustomerWithID(int id) {
        rest().
                when().get(BASE_URL + CUSTOMER + "{+" + id + "+}", id).
                then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Step
    public void putCustomerWithID(Customer customer) {
        rest().
                given().contentType("application/xml").content(getXMLCustomer(customer)).
                when().put(BASE_URL + CUSTOMER).
                then().assertThat().statusCode(HttpStatus.SC_OK);

        shouldVerifyCustomerBody(customer);
    }

    @Step
    public void shouldVerifyCustomerBody(Customer customer) {
        rest().
                when().
                    get(BASE_URL + CUSTOMER + "{+" + customer.getId() + "+}", customer.getId()).
                then().
                    assertThat().rootPath("CUSTOMER").
                and().
                    body(
                        "FIRSTNAME", is(customer.getFirstName()),
                        "LASTNAME", is(customer.getLastName()),
                        "STREET", is(customer.getStreet()),
                        "CITY", is(customer.getCity())
                        );
    }

    @Step
    public void postCustomerWithID(Customer customer) {
        rest().
                given().contentType("application/xml").content(getXMLCustomer(customer)).
                when().post(BASE_URL + CUSTOMER).
                then().assertThat().statusCode(HttpStatus.SC_CREATED);

        shouldVerifyCustomerBody(customer);
    }

    private String getXMLCustomer(Customer customer) {
        return "<CUSTOMER xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                    "  <ID>" + customer.getId() + "</ID>\n" +
                    "  <FIRSTNAME>" + customer.getFirstName() + "</FIRSTNAME>\n" +
                    "  <LASTNAME>" + customer.getLastName() + "</LASTNAME>\n" +
                    "  <STREET>" + customer.getStreet() + "</STREET>\n" +
                    "  <CITY>" + customer.getCity() + "</CITY>\n" +
                    "</CUSTOMER>";
    }

    @Step
    public void deleteCustomerWithID(int id) {
        rest().
                when().delete(BASE_URL + CUSTOMER+ "{+" + id + "+}", id).
                then().assertThat().statusCode(HttpStatus.SC_OK);
    }
}