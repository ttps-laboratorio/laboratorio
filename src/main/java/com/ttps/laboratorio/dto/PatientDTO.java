package com.ttps.laboratorio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    @NotBlank(message = "Patient name is required")
    private String firstName;

    @NotBlank(message = "Patient lastname is required")
    private String lastName;

    @NotNull(message = "Patient birthdate is required")
    private LocalDate birthDate;

    @NotBlank(message = "Patient clinic history is required")
    private String clinicHistory;

    private String affiliateNumber;

    private ContactDTO contact;

    private HealthInsuranceDTO healthInsurance;

    private StudyDTO studies;





}