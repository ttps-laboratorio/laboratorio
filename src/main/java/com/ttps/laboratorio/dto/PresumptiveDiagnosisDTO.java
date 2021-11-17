package com.ttps.laboratorio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PresumptiveDiagnosisDTO {

    private long id;

    @NotNull(message = "Diagnosis description is required")
    private String description;

}