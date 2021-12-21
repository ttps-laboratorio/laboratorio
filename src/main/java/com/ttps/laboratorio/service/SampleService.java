package com.ttps.laboratorio.service;

import org.springframework.stereotype.Service;

import com.ttps.laboratorio.dto.request.SampleDTO;
import com.ttps.laboratorio.entity.Sample;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.repository.ISampleRepository;

@Service
public class SampleService {

	private final ISampleRepository sampleRepository;

	private final StudyService studyService;

	public SampleService(ISampleRepository sampleRepository, StudyService studyService) {
		this.sampleRepository = sampleRepository;
		this.studyService = studyService;
	}

	public Sample createSample(Long studyId, SampleDTO request) {
		Study study = studyService.getStudy(studyId);
		if (study.getActualStatus() != null && !study.getActualStatus().getId().equals(StudyStatus.ESPERANDO_TOMA_DE_MUESTRA)) {
			throw new BadRequestException(
					"El estudio #" + studyId
							+ " no se encuentra en el estado correspondiente para ingresar datos de muestra.");
		}
		Sample sample = new Sample();
		if (study.getSample() != null) {
			sample = updateSample(study, request);
		} else {
			sample.setMilliliters(request.getMilliliters());
			sample.setFreezer(request.getFreezer());
			sample.setStudy(study);
			study.setSample(sample);
		}
		sampleRepository.save(sample);
		studyService.setCheckpointWithStatus(StudyStatus.ESPERANDO_RETIRO_DE_MUESTRA, study);
		studyService.saveStudy(study);
		return sample;
	}

	private Sample updateSample(Study study, SampleDTO request) {
		study.getSample().setFreezer(request.getFreezer());
		study.getSample().setMilliliters(request.getMilliliters());
		study.getSample().setSampleBatch(null);
		return study.getSample();
	}

}
