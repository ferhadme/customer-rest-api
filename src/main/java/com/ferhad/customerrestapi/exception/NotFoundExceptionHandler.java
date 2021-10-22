package com.ferhad.customerrestapi.exception;

import com.ferhad.customerrestapi.exception.customer.CustomerNotFoundException;
import com.ferhad.customerrestapi.exception.customer.CustomerExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class NotFoundExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<CustomerExceptionMessage> customerNotFoundExceptionHandler() {
        CustomerExceptionMessage customerNotFoundExceptionMessage =
                new CustomerExceptionMessage(HttpStatus.NOT_FOUND, "Customer not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(customerNotFoundExceptionMessage);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        CustomerExceptionMessage badRequestForNameParam =
                new CustomerExceptionMessage(HttpStatus.BAD_REQUEST, "Bad request for name parameter");
        return ResponseEntity
                .badRequest()
                .body(badRequestForNameParam);
    }
}
