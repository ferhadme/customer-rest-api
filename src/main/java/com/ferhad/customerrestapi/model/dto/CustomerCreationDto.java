package com.ferhad.customerrestapi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CustomerCreationDto {
    @JsonIgnore
    private Long id;

    @NotNull
    private String name;

    @JsonIgnore
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    private LocalDateTime editedAt = LocalDateTime.now();
}
