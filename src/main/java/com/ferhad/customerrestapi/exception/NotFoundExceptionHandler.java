package com.ferhad.customerrestapi.exception.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus
public class NotFoundExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<CustomerNotFoundExceptionMessage> customerNotFoundExceptionHandler() {
        CustomerNotFoundExceptionMessage customerNotFoundExceptionMessage =
                new CustomerNotFoundExceptionMessage(HttpStatus.NOT_FOUND, "Customer not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(customerNotFoundExceptionMessage);
    }
}
