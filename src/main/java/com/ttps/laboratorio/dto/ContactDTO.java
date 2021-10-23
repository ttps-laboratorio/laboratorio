package com.ttps.laboratorio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    @NotBlank(message = "Patient name is required")
    private String name;

    @NotNull(message = "Patient name is required")
    private Integer phoneNumber;

    @NotBlank(message = "Patient name is required")
    private String email;




}
