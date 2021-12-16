package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.request.SampleDTO;
import com.ttps.laboratorio.entity.Sample;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.repository.ISampleRepository;
import org.springframework.stereotype.Service;

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
		if (study.getActualStatus() != null && !study.getActualStatus().getId().equals(5L)) {
			throw new BadRequestException(
					"El estudio no se encuentra en el estado correspondiente para ingresar datos de muestra.");
		}
		Sample sample = new Sample();
		sample.setMilliliters(request.getMilliliters());
		sample.setFreezer(request.getFreezer());
		sample.setStudy(study);
		sampleRepository.save(sample);
		study.setSample(sample);
		studyService.setCheckpointWithStatus(6L, study);
		studyService.saveStudy(study);
		return sample;
	}

}
