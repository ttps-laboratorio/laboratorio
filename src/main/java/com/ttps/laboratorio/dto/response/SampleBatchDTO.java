package com.ttps.laboratorio.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ttps.laboratorio.entity.SampleBatchStatus;

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

	private String finalReportsUrl;

	private List<SampleDTO> samples = new ArrayList<>();

}
