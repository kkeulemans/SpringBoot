package nl.novi.testjunitjupiter.service;

import nl.novi.testjunitjupiter.TestJunitJupiterApplication;
import nl.novi.testjunitjupiter.model.Customer;
import nl.novi.testjunitjupiter.repository.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes={TestJunitJupiterApplication.class})
@EnableConfigurationProperties
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Mock
    Customer customer;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testGetCustomerByLastName() {
        customer = new Customer("Albert", "Einstein");

        Mockito
                .when(customerRepository.findByLastName("Einstein"))
                .thenReturn(customer);

        String name = "Einstein";
        String expected = "Albert Einstein";

        Customer found = customerService.getCustomerByLastName(name);

        assertEquals(expected, found.getFullName());
    }

    @Test
    public void testGetCustomerByLastName2() {
        customer = new Customer("Albert", "Einstein");

        Mockito
                .doReturn(customer)
                .when(customerRepository)
                .findByLastName("Einstein");

        String name = "Einstein";
        String expected = "Albert Einstein";

        Customer found = customerService.getCustomerByLastName(name);

        assertEquals(expected, found.getFullName());
    }

    @Test
    void testGetCustomerByLastNameNotFound() {
        String name = "EinsteinXXX";

        // Setup our mock repository
        Mockito
                .doReturn(null).when(customerRepository)
                .findByLastName(name);

        // Execute the service call
        Customer found = customerService.getCustomerByLastName(name);

        // Assert the response
        assertNull(found, "Widget should not be found");
    }


}
