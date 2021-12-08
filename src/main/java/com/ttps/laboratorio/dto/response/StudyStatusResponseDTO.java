package com.ttps.laboratorio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyStatusResponseDTO {

	private Long id;

	private String name;

	private Integer order;

	private String nextName;

}
