package com.ttps.laboratorio.dto.request;

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
public class GuardianDTO {

  @NotBlank(message = "Guardian firstname is required")
  private String firstName;

  @NotBlank(message = "Guardian lastname is required")
  private String lastName;

  @NotBlank(message = "Contact phone number is required")
  private String phoneNumber;

  @NotBlank(message = "Contact email is required")
  @Email(message = "Invalid email")
  private String email;

  @NotBlank(message = "Contact address is required")
  private String address;

}
