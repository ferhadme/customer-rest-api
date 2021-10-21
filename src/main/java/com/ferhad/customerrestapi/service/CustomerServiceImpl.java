package com.ferhad.customerrestapi.service;

import com.ferhad.customerrestapi.exception.customer.CustomerNotFoundException;
import com.ferhad.customerrestapi.model.Customer;
import com.ferhad.customerrestapi.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Customer> page = customerRepository.findAll(pageable);
        return page.hasContent() ?
                page.getContent() :
                new ArrayList<>();
    }

    @Override
    public Customer get(Long id) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    // TODO: 10/21/21 ALL DAO - DTO conversions happens in service
}
