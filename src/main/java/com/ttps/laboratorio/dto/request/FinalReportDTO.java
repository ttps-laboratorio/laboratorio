package com.ttps.laboratorio.dto.request;

import javax.validation.constraints.NotBlank;
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
public class FinalReportDTO {

	@NotNull(message = "Result is required.")
	private Boolean positiveResult;

	@NotBlank(message = "Final report is required.")
	private String report;

}
