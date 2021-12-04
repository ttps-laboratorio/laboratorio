package com.ttps.laboratorio.dto.request;

import java.math.BigDecimal;
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
public class StudyDTO {

	@NotNull(message = "Study budget is required")
	private BigDecimal budget;

	@NotNull(message = "Study extraction amount is required")
	private BigDecimal extractionAmount;

	@NotNull(message = "Study referring doctor is required")
	private Integer referringDoctorId;

	@NotNull(message = "Study type is required")
	private Integer studyTypeId;

	@NotNull(message = "Study's presumptive diagnosis is required")
	private Integer presumptiveDiagnosisId;

}
