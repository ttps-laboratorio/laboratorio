package com.ttps.laboratorio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttps.laboratorio.dto.request.SampleDTO;
import com.ttps.laboratorio.entity.Sample;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.exception.BadRequestException;

@Service
public class SampleService {

	private final StudyService studyService;

	public SampleService(StudyService studyService) {
		this.studyService = studyService;
	}

	@Transactional
	public Sample createSample(Long studyId, SampleDTO request) {
		Study study = studyService.getStudyById(studyId);
		if (study.getActualStatus() != null && !study.getActualStatus().getId().equals(StudyStatus.ESPERANDO_TOMA_DE_MUESTRA)) {
			throw new BadRequestException(
					"El estudio #" + studyId
							+ " no se encuentra en el estado correspondiente para ingresar datos de muestra.");
		}
		Sample sample = Sample.builder().freezer(request.getFreezer()).milliliters(request.getMilliliters()).build();
		study.setSample(sample);
		studyService.addCheckpointWithLoggedUser(StudyStatus.ESPERANDO_RETIRO_DE_MUESTRA, study);
		studyService.saveStudy(study);
		return sample;
	}

}
