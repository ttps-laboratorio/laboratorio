package com.ttps.laboratorio.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ttps.laboratorio.entity.SampleBatchStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SampleBatchResponseDTO {

  private Long id;

  private SampleBatchStatus status;

  private String finalReportsUrl;

}
