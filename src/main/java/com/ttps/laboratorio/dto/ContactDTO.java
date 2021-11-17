package com.ttps.laboratorio.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    @NotBlank(message = "Contact name is required")
    private String name;

    @NotNull(message = "Contact phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Contact email is required")
    private String email;


}
