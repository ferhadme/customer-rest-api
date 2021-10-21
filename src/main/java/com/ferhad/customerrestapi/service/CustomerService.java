package com.ferhad.customerrestapi.service;

import com.ferhad.customerrestapi.exception.customer.CustomerNotFoundException;
import com.ferhad.customerrestapi.model.Customer;
import com.ferhad.customerrestapi.model.dto.CustomerCreationDto;
import com.ferhad.customerrestapi.model.dto.CustomerGetDto;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll(Integer pageNo, Integer pageSize, String sortBy);

    Customer get(Long id) throws CustomerNotFoundException;

    Customer save(Customer customerDto);

    void delete(Long id);
}
