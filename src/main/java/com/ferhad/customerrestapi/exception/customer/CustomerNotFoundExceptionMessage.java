package com.ferhad.customerrestapi.exception.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CustomerNotFoundExceptionMessage {
    private HttpStatus status;
    private String msg;
}
