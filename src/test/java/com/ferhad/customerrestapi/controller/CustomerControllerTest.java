package com.ferhad.customerrestapi.controller;

import com.ferhad.customerrestapi.exception.CustomerExceptionHandler;
import com.ferhad.customerrestapi.exception.customer.CustomerNotFoundException;
import com.ferhad.customerrestapi.model.Customer;
import com.ferhad.customerrestapi.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .name("Farhad")
                .build();
    }

    @Test
    void saveCustomer() throws Exception {
        Customer customerInp = Customer.builder()
                .name("Farhad")
                .build();

        Mockito.when(customerService.save(customerInp))
                .thenReturn(customer);

        mockMvc.perform(
                MockMvcRequestBuilders.post(CustomerController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\t\"name\":\"Farhad\"\n" +
                                "}")
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void fetchCustomer() throws Exception {
        Mockito.when(customerService.get(1L))
                .thenReturn(customer);

        mockMvc.perform(
                MockMvcRequestBuilders.get(CustomerController.ENDPOINT + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value(customer.getName()));
    }

    @Test
    void fetchNonExistCustomer() throws CustomerNotFoundException {
        Mockito.when(customerService.get(2L))
                .thenThrow(new CustomerNotFoundException(CustomerExceptionHandler.CUSTOMER_NOT_FOUND));

        CustomerNotFoundException exception = Assertions.assertThrows(CustomerNotFoundException.class,
                () -> { customerService.get(2L); }
        );

        assertEquals(CustomerExceptionHandler.CUSTOMER_NOT_FOUND, exception.getMessage());
    }
}

