package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.request.UrlDTO;
import com.ttps.laboratorio.dto.response.SampleBatchDTO;
import com.ttps.laboratorio.entity.Sample;
import com.ttps.laboratorio.entity.SampleBatch;
import com.ttps.laboratorio.entity.SampleBatchStatus;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.ISampleBatchRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SampleBatchService {

	private final ISampleBatchRepository sampleBatchRepository;

	private final StudyService studyService;

	public static final Integer SAMPLE_BATCH_COUNT = 10;

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

	public SampleBatchDTO uploadResults(Long sampleBatchId, UrlDTO urlDTO) {
		SampleBatch sampleBatch = sampleBatchRepository.findById(sampleBatchId)
				.orElseThrow(() -> new NotFoundException("No existe un lote con el id " + sampleBatchId + "."));
		if (!SampleBatchStatus.IN_PROCESS.equals(sampleBatch.getStatus())) {
			throw new BadRequestException("El lote #" + sampleBatchId + " ya tiene cargado los resultados.");
		}
		sampleBatch.setFinalReportsUrl(urlDTO.getUrl());
		List<Sample> failedSamples = new ArrayList<>();
		List<Sample> successfulSamples = new ArrayList<>();
		sampleBatch.getSamples().forEach(sample -> {
			if (urlDTO.getFailedSamples().contains(sample.getId())) {
				failedSamples.add(sample);
			} else {
				successfulSamples.add(sample);
			}
		});
		List<Study> failedStudies = new ArrayList<>();
		List<Study> successfulStudies = new ArrayList<>();
		failedSamples.forEach(sample -> failedStudies.add(studyService.getStudyBySample(sample)));
		failedStudies.forEach(study -> {
			studyService.setCheckpointWithStatus(StudyStatus.ESPERANDO_SELECCION_DE_TURNO, study);
			studyService.saveStudy(study);
		});
		successfulSamples.forEach(sample -> successfulStudies.add(studyService.getStudyBySample(sample)));
		successfulStudies.forEach(study -> {
			studyService.setCheckpointWithStatus(StudyStatus.ESPERANDO_INTERPRETACION_DE_RESULTADOS, study);
			studyService.saveStudy(study);
		});
		sampleBatch.setStatus(SampleBatchStatus.PROCESSED);
		sampleBatchRepository.save(sampleBatch);
		return getSampleBatch(sampleBatchId);
	}

}
