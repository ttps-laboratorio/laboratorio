package com.ttps.laboratorio.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class DoctorDTO {

  @NotBlank(message = "Doctor first name is required")
  private String firstName;

  @NotBlank(message = "Doctor last name is required")
  private String lastName;

  @NotBlank(message = "Doctor email is required")
  @Email(message = "Invalid email")
  private String email;

  private Integer phoneNumber;

  @NotNull(message = "Doctor last name is required")
  private Integer licenseNumber;

}
