package com.ttps.laboratorio.dto.request;

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
public class PresumptiveDiagnosisDTO {

	@NotNull(message = "Presumptive diagnosis id is required")
	private long id;

}
