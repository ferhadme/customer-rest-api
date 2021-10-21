package com.ferhad.customerrestapi.controller;

import com.ferhad.customerrestapi.exception.customer.CustomerNotFoundException;
import com.ferhad.customerrestapi.model.Customer;
import com.ferhad.customerrestapi.model.dto.CustomerCreationDto;
import com.ferhad.customerrestapi.model.dto.CustomerGetDto;
import com.ferhad.customerrestapi.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    private static final Logger logger = Logger.getLogger("Controller_Logger");

    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<CustomerGetDto>> getAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        List<Customer> customers = customerService.getAll(pageNo, pageSize, sortBy);
        List<CustomerGetDto> customerGetDtos = customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerGetDto.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(customerGetDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerGetDto> get(@PathVariable Long id) throws CustomerNotFoundException {
        Customer customer = customerService.get(id);
        CustomerGetDto customerGetDto = modelMapper.map(customer, CustomerGetDto.class);
        return ResponseEntity
                .ok(customerGetDto);
    }

    @PostMapping
    public ResponseEntity<CustomerCreationDto> save(@RequestBody @Valid CustomerCreationDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customerService.save(customer);
        logger.info("Customer ====> " + customer);
        URI customerLocation = URI.create(String.format("/api/v1/customers/%s", customer.getId()));

        return ResponseEntity
                .created(customerLocation)
                .body(modelMapper.map(customer, CustomerCreationDto.class));
    }
}










