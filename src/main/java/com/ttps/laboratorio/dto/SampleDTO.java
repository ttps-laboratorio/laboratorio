package com.ttps.laboratorio.dto;

import com.ttps.laboratorio.entity.SampleBatch;
import com.ttps.laboratorio.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SampleDTO {

	@NotNull(message = "Sample's mililiters are required")
	private Double mililiters;

	@NotNull(message = "Sample's mililiters are required")
	private Integer freezer;

	private long study;

	private long sampleBatch;

}
