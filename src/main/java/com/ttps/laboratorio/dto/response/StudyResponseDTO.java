package com.ttps.laboratorio.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.entity.Doctor;
import com.ttps.laboratorio.entity.Extractionist;
import com.ttps.laboratorio.entity.PresumptiveDiagnosis;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.entity.StudyType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudyResponseDTO {

  private Long id;

  private LocalDateTime createdAt;

  private BigDecimal budget;

  private BigDecimal extractionAmount;

  private Boolean paidExtractionAmount;

  private PatientResponseDTO patient;

  private Appointment appointment;

  private Doctor referringDoctor;

  private StudyType type;

  private PresumptiveDiagnosis presumptiveDiagnosis;

  private StudyStatus actualStatus;

  private SampleResponseDTO sample;

  private Extractionist extractionist;

}
