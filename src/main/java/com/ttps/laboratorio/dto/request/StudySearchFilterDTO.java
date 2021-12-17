package com.ttps.laboratorio.dto.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class StudySearchFilterDTO implements Serializable {

	private Long dni;
	private Long studyTypeId;
	private Long studyStatusId;
}
