package com.ttps.laboratorio.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SampleResponseDTO {

  private Long id;

  private Double milliliters;

  private Integer freezer;

  private Boolean failed;

  private Long studyId;

  private SampleBatchResponseDTO sampleBatch;

}
