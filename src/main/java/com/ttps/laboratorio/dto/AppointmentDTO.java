package com.ttps.laboratorio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    @NotNull(message = "The appointment's date is required")
    private LocalDate date;

    @NotNull(message = "The appointment's schedule is required")
    private LocalTime time;

}
