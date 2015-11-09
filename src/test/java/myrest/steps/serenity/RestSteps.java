package myrest.steps.serenity;

import myrest.model.Customer;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.apache.http.HttpStatus;

import java.net.URI;
import java.net.URISyntaxException;

import static net.serenitybdd.rest.SerenityRest.rest;

public class RestSteps extends ScenarioSteps {

    public static final String BASE_URL = "http://localhost:8080/sqlrest/";
    public static final String CUSTOMER = "CUSTOMER/";

    Customer customer;

    @Step
    public void getAllCustomers() {
        URI uri = null;
        try {
            uri = new URI(BASE_URL + CUSTOMER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        rest().get(uri).then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Step
    public void getCustomerWithID(int id) {
        rest().get(BASE_URL + CUSTOMER + "{+" + id + "+}", id).then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Step
    public void putCustomerWithID(Customer customer) {
        this.customer = customer;

        rest().given().contentType("application/xml")
                .content(getXMLCustomer(customer)).put(BASE_URL + CUSTOMER);
    }

    @Step
    public void postCustomerWithID(Customer customer) {
        this.customer = customer;

        rest().given().contentType("application/xml")
                .content(getXMLCustomer(customer)).post(BASE_URL + CUSTOMER);
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
}