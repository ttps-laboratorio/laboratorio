package com.ttps.laboratorio.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
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

	@NotNull(message = "Patient dni is required")
	private Long dni;

	@NotBlank(message = "Patient name is required")
	private String firstName;

	@NotBlank(message = "Patient lastname is required")
	private String lastName;

	@NotNull(message = "Patient birth date is required")
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "America/Argentina/Buenos_Aires")
	private LocalDate birthDate;

	@NotBlank(message = "Patient clinic history is required")
	private String clinicHistory;

	@Valid
	private ContactDTO contact;

	private String affiliateNumber;

	private HealthInsuranceDTO healthInsurance;

}
