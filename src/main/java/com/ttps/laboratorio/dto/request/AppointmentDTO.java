package com.ttps.laboratorio.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.FutureOrPresent;
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
public class AppointmentDTO {

	@NotNull(message = "Appointment date is required")
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "America/Argentina/Buenos_Aires")
	@FutureOrPresent(message = "The date entered must be later than the current one")
	private LocalDate date;

	@NotNull(message = "Appointment time is required")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "America/Argentina/Buenos_Aires")
	private LocalTime time;

}
