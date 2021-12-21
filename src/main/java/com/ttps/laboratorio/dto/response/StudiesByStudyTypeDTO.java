package com.ttps.laboratorio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudiesByStudyTypeDTO {

	private String studyTypeName;

	private Integer studies;

}
