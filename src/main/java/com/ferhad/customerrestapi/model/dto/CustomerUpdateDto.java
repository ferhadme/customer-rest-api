package com.ferhad.customerrestapi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerUpdateDto {
    @NotNull
    private String name;

    @JsonIgnore
    private final LocalDateTime editedAt = LocalDateTime.now();
}
