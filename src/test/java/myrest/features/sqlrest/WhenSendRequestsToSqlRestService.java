package myrest.features.sqlrest;

import myrest.model.Customer;
import myrest.steps.serenity.RestSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

@RunWith(SerenityRunner.class)
public class WhenSendRequestsToSqlRestService {

    @Steps
    public RestSteps restSteps;

    @Test
    public void shouldGetAllCustomersWithoutErrors()  {
        restSteps.getAllCustomers();

    }

    @Test
    public void shouldGetCustomerWithID() {
        restSteps.getCustomerWithID(1);
        restSteps.getCustomerWithID(11);
        restSteps.getCustomerWithID(45);
    }

    @Test
    public void shouldCreateCustomerWithID() {
        int id = Math.abs(new Random().nextInt());
        Customer customer = getCustomer();
        customer.setId(id);
        restSteps.postCustomerWithID(customer);
        restSteps.getCustomerWithID(id);
    }

    @Test
    public void shouldUpdateCustomerWithID() {
        Customer customer = getCustomer();
        restSteps.putCustomerWithID(customer);
    }

    private Customer getCustomer() {
        final int id=1;
        final String firstName="fname";
        final String lastName="lname";
        final String street="street";
        final String city="city";
        return new Customer(id,firstName,lastName,street,city);
    }

    @Test
    public void shouldDeleteCustomerWithID() {
        int id = Math.abs(new Random().nextInt());
        Customer customer = getCustomer();
        customer.setId(id);
        restSteps.postCustomerWithID(customer);
        restSteps.getCustomerWithID(id);
        restSteps.deleteCustomerWithID(id);
        restSteps.getNonExistingCustomerWithID(id);
    }

}