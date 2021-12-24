package com.ttps.laboratorio.dto.request;

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
public class PatientUserDTO {

  @NotNull(message = "Patient dni is required")
  private Integer dni;

  @NotNull(message = "Patient user is required")
  private UserRequestDTO user;

}
