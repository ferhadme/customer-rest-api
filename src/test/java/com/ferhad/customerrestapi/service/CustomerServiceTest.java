package com.ferhad.customerrestapi.service;

import com.ferhad.customerrestapi.exception.CustomerExceptionHandler;
import com.ferhad.customerrestapi.exception.customer.CustomerNotFoundException;
import com.ferhad.customerrestapi.model.Customer;
import com.ferhad.customerrestapi.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CustomerServiceTest {
    private static final Logger logger = Logger.getLogger("test.CustomerServiceTest");

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder()
                .name("Farhad")
                .id(1L)
                .build();

        Mockito.when(customerRepository.findByName("Farhad"))
                .thenReturn(Optional.of(customer));

        Mockito.when(customerRepository.findByName("Togrul"))
                .thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("Get name of valid customer")
    public void whenValidCustomerName_thenCustomerShouldBeFound() throws CustomerNotFoundException {
        Customer customer = customerService.getByName("Farhad");
        assertEquals("Farhad", customer.getName());
    }

    @Test
    @DisplayName("Get name of invalid customer")
    public void whenInvalidCustomerName_thenExceptionShouldBeThrown() {
        CustomerNotFoundException exception = Assertions.assertThrows(CustomerNotFoundException.class,
                () -> customerService.getByName("Togrul"));
        logger.info("Exception when customer not found ====> " + exception.getMessage());
        assertEquals(CustomerExceptionHandler.CUSTOMER_NOT_FOUND, exception.getMessage());
    }

}