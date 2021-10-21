package com.ferhad.customerrestapi.service;

import com.ferhad.customerrestapi.exception.customer.CustomerNotFoundException;
import com.ferhad.customerrestapi.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll(Integer pageNo, Integer pageSize, String sortBy);

    Customer get(Long id) throws CustomerNotFoundException;

    Customer save(Customer customer);
}
