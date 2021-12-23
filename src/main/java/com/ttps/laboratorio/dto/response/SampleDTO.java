package com.ttps.laboratorio.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampleDTO implements Serializable {

	private static final long serialVersionUID = -5000253122926959427L;

	private Long id;
	private Long studyId;
	private Boolean failed;
	private Double milliliters;
	private Integer freezer;

}
