package com.ttps.laboratorio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ttps.laboratorio.entity.HealthInsurance;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.Valid;
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
public class PatientDTO {

  @NotBlank(message = "Patient dni is required")
  private String dni;

  @NotBlank(message = "Patient first name is required")
  private String firstName;

  @NotBlank(message = "Doctor last name is required")
  private String lastName;

  @NotNull(message = "Patient birth date is required")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Argentina/Buenos_Aires")
  private LocalDate birthDate;

  @NotBlank(message = "Patient clinic history is required")
  private String clinicHistory;

  @Valid
  private ContactDTO contact;

  private String affiliateNumber;

  private HealthInsurance healthInsurance;

}
