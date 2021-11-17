package com.ttps.laboratorio.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

  @NotBlank(message = "Patient contact name is required")
  private String name;

  @NotBlank(message = "Patient contact phone number is required")
  private String phoneNumber;

  @NotBlank(message = "Patient contact email is required")
  @Email(message = "Invalid email")
  private String email;

}
