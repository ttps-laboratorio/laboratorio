package com.ttps.laboratorio.dto;

import com.ttps.laboratorio.entity.Doctor;
import com.ttps.laboratorio.entity.PresumptiveDiagnosis;
import com.ttps.laboratorio.entity.StudyType;
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
  private Doctor referringDoctor;

  @NotNull(message = "Study type is required")
  private StudyType studyType;

  @NotNull(message = "Study type is required")
  private PresumptiveDiagnosis presumptiveDiagnosis;

}
