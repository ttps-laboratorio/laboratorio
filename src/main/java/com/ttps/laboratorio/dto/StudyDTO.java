package com.ttps.laboratorio.dto;

import com.ttps.laboratorio.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudyDTO {

	private LocalDateTime created_at;

	@NotNull(message = "Study budget is required")
	private BigDecimal budget;

	@NotNull(message = "Study's extraction amount is required")
	private BigDecimal extractionAmount;

	private Boolean paidExtractionAmount;

	// private Boolean positiveResult;

	private Boolean delay;

	private PatientDTO patient;

	private AppointmentDTO appointment;

	private DoctorDTO referringDoctor;

	private StudyTypeDTO type;

	private ExtractionistDTO extractionist;

	@NotNull(message = "Study's presumptive diagnosis is required")
	private PresumptiveDiagnosisDTO presumptiveDiagnosis;

	private SampleDTO sample;

	// checkpoints que no sé

}
