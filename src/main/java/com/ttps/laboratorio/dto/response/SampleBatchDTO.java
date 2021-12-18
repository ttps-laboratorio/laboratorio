package com.ttps.laboratorio.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ttps.laboratorio.entity.Sample;
import com.ttps.laboratorio.entity.SampleBatchStatus;
import com.ttps.laboratorio.entity.Study;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SampleBatchDTO {

	private Long id;

	private SampleBatchStatus status;

	private List<Study> studies;

	private String FinalReportsUrl;

	private List<Sample> samples;

}
