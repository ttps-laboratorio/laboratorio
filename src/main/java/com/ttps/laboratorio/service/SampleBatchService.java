package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.response.SampleBatchDTO;
import com.ttps.laboratorio.entity.SampleBatch;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.ISampleBatchRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SampleBatchService {

	private final ISampleBatchRepository sampleBatchRepository;

	private final StudyService studyService;

	public SampleBatchService(ISampleBatchRepository sampleBatchRepository, StudyService studyService) {
		this.sampleBatchRepository = sampleBatchRepository;
		this.studyService = studyService;
	}

	public void createBatch(List<Study> studiesReadyForProcess) {
		SampleBatch sampleBatch = new SampleBatch();
		studiesReadyForProcess.forEach(study -> sampleBatch.addSample(study.getSample()));
		sampleBatchRepository.save(sampleBatch);
	}

	public SampleBatchDTO getSampleBatch(Long id) {
		SampleBatch sampleBatch =
				sampleBatchRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un lote con el id " + id + "."));
		SampleBatchDTO response = new SampleBatchDTO();
		response.setStatus(sampleBatch.getStatus());
		response.setId(sampleBatch.getId());
		response.setFinalReportsUrl(sampleBatch.getFinalReportsUrl());
		response.setSamples(sampleBatch.getSamples());
		sampleBatch.getSamples().forEach(sample -> response.getStudies().add(studyService.getStudyBySample(sample)));
		return response;
	}

	public List<SampleBatch> getAllSampleBatches() {
		return new ArrayList<>(sampleBatchRepository.findAll());
	}

}
