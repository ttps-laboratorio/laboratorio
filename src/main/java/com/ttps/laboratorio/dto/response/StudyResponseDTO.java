package com.ttps.laboratorio.dto.response;

import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.entity.Checkpoint;
import com.ttps.laboratorio.entity.Doctor;
import com.ttps.laboratorio.entity.Extractionist;
import com.ttps.laboratorio.entity.FinalReport;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.entity.PresumptiveDiagnosis;
import com.ttps.laboratorio.entity.Sample;
import com.ttps.laboratorio.entity.StudyType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyResponseDTO {

	private Long id;

	private LocalDateTime createdAt;

	private BigDecimal budget;

	private BigDecimal extractionAmount;

	private Boolean paidExtractionAmount;

	private FinalReport finalReport;

	private Boolean delay = false;

	private Patient patient;

	private Appointment appointment;

	private Doctor referringDoctor;

	private StudyType type;

	private Extractionist extractionist;

	private PresumptiveDiagnosis presumptiveDiagnosis;

	private Sample sample;

	private List<Checkpoint> checkpoints;

	private StudyStatusResponseDTO actualStatus;
}
