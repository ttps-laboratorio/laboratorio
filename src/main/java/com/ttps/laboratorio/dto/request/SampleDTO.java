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
public class SampleDTO {

	@NotNull(message = "Sample's milliliters are required")
	private Double milliliters;

	@NotNull(message = "Sample's freezer is required")
	private Integer freezer;

	private long study;

	private long sampleBatch;

}
