package com.ttps.laboratorio.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
public class HealthInsuranceDTO {

  @NotBlank(message = "Health insurance name is required")
  private String name;

  @NotNull(message = "Health insurance phone number is required")
  private Integer phoneNumber;

  @NotBlank(message = "Health insurance email is required")
  private String email;

}
