package com.ttps.laboratorio.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDTO {

  private Long id;

  private Long dni;

  private String firstName;

  private String lastName;

  private LocalDate birthDate;

}
