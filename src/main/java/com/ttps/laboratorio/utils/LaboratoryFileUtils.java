package com.ttps.laboratorio.utils;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ttps.laboratorio.constants.FileNameConstants;

@Component
public class LaboratoryFileUtils {

	@Value("${laboratory.properties.save-files-path}")
	private String fileBasePath;

	private String patientBaseName(Long patientId) {
		return fileBasePath + File.separator + patientId;
	}

	private String patientStudyBaseName(Long patientId, Long studyId) {
		return patientBaseName(patientId) + "_" + studyId;
	}

	public String getFilenameBudget(Long patientId, Long studyId) {
		return patientStudyBaseName(patientId, studyId) + FileNameConstants.BUDGET_PDF;
	}
}
