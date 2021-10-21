package com.ferhad.customerrestapi.controller;

import com.ferhad.customerrestapi.exception.customer.CustomerNotFoundException;
import com.ferhad.customerrestapi.model.Customer;
import com.ferhad.customerrestapi.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity
                .ok(customerService.getAll(pageNo, pageSize, sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable Long id) throws CustomerNotFoundException {
        return ResponseEntity
                .ok(customerService.get(id));
    }

    @PostMapping
    public ResponseEntity<Customer> save(@RequestBody @Valid Customer customer) {
        Customer customerSaved = customerService.save(customer);
        URI location = URI.create(String.format("/customers/%s", customerSaved.getId()));
        return ResponseEntity
                .created(location)
                .body(customerSaved);
    }
}










