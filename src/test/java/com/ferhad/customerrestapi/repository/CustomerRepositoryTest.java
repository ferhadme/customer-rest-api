package com.ferhad.customerrestapi.repository;

import com.ferhad.customerrestapi.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder()
                .name("Farhad")
                .build();

        entityManager.persist(customer);
    }

    @Test
    void whenGetById_thenReturnCustomer() {
        Customer customer = customerRepository.getById(1L);
        assertEquals(customer.getName(), "Farhad");
    }

}