package com.ferhad.customerrestapi.exception.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerExceptionMessage {
    private HttpStatus status;
    private String msg;
}
