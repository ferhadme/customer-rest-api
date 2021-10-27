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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
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
        Optional<Customer> customer = Optional.of(
                Customer.builder()
                .name("Farhad")
                .id(1L)
                .build());

        Mockito.when(customerRepository.findByName("Farhad"))
                .thenReturn(customer);

        Mockito.when(customerRepository.findByName("Togrul"))
                .thenReturn(Optional.empty());

        Mockito.when(customerRepository.findById(1L))
                .thenReturn(customer);

        Mockito.when(customerRepository.findById(2L))
                .thenReturn(Optional.empty());

        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Customer el = Mockito.mock(Customer.class);
            logger.info("Mocked customer el =====> " + el.getId() + "; " + el.getName());
            customers.add(el);
        }
        PageImpl<Customer> page = new PageImpl<>(customers);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Mockito.when(customerRepository.findAll(pageable))
                .thenReturn(page);
    }

    @Test
    @DisplayName("Get name of valid customer")
    void whenValidCustomerName_thenCustomerShouldBeFound() throws CustomerNotFoundException {
        Customer customer = customerService.getByName("Farhad");
        assertEquals(
                "Farhad", customer.getName()
        );
    }

    @Test
    @DisplayName("Get name of invalid customer")
    void whenInvalidCustomerName_thenExceptionShouldBeThrown() {
        CustomerNotFoundException exception = Assertions.assertThrows(CustomerNotFoundException.class,
                () -> customerService.getByName("Togrul"));
        logger.info("Exception when customer not found ====> " + exception.getMessage());
        assertEquals(
                CustomerExceptionHandler.CUSTOMER_NOT_FOUND, exception.getMessage()
        );
    }

    @Test
    @DisplayName("Get id of valid customer")
    void whenValidCustomerId_thenCustomerShouldBeFound() throws CustomerNotFoundException {
        Customer customer = customerService.get(1L);
        assertEquals(
                1L, customer.getId()
        );
    }

    @Test
    @DisplayName("Get id of invalid customer")
    void whenInvalidCustomerId_thenExceptionShouldBeThrown() {
        CustomerNotFoundException exception = Assertions.assertThrows(CustomerNotFoundException.class,
                () -> customerService.get(2L));
        assertEquals(
                CustomerExceptionHandler.CUSTOMER_NOT_FOUND, exception.getMessage()
        );
    }

    @Test
    @DisplayName("Get all customers")
    void whenAllCustomers_thenAllCustomersShouldBeReturned() {
        List<Customer> customers = customerService.getAll(0, 10, "id");
        assertEquals(
                10, customers.size()
        );
    }

    @Test
    @DisplayName("Save new customer")
    void whenNewCustomerSaved_thenCustomerShouldBeSaved() {
        Customer customer = Mockito.mock(Customer.class);
        Mockito.when(customerRepository.save(customer))
                        .thenReturn(customer);
        assertEquals(
                customer, customerService.save(customer)
        );
    }


    @Test
    @DisplayName("Delete customer")
    void deleteCustomer_thenCustomerShouldBeDeleted() throws CustomerNotFoundException {
        // Optional<Customer> customer = Optional.of(
        //         Customer.builder()
        //                 .name("Ali")
        //                 .id(10L)
        //                 .build());

        // Mockito.when(customerRepository.save(customer.get()))
        //         .thenReturn(customer.get());
        // Mockito.when(customerRepository.findById(10L))
        //         .thenReturn(customer);

        // Customer customerGet = customerService.get(10L);
        // assertEquals(10L, customerGet.getId());

        // CustomerNotFoundException exception = Assertions.assertThrows(CustomerNotFoundException.class,
        //         () -> {
        //             customerService.delete(10L);
        //             Customer customerDeleted = customerService.get(10L);
        //         });
        // assertEquals(
        //         CustomerExceptionHandler.CUSTOMER_NOT_FOUND, exception.getMessage()
        // );


    }




}