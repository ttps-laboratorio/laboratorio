package com.ttps.laboratorio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttps.laboratorio.dto.request.UrlDTO;
import com.ttps.laboratorio.dto.response.SampleBatchDTO;
import com.ttps.laboratorio.dto.response.SampleDTO;
import com.ttps.laboratorio.entity.Sample;
import com.ttps.laboratorio.entity.SampleBatch;
import com.ttps.laboratorio.entity.SampleBatchStatus;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.ISampleBatchRepository;

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
		response.setSamples(sampleBatch.getSamples().stream().map(
				s -> new SampleDTO(s.getId(), s.getStudy().getId(), s.getFailed(), s.getMilliliters(), s.getFreezer()))
				.collect(Collectors.toList()));
		return response;
	}

	public List<SampleBatch> getAllSampleBatches() {
		return new ArrayList<>(sampleBatchRepository.findAll());
	}

	@Transactional
	public SampleBatchDTO uploadResults(Long sampleBatchId, UrlDTO urlDTO) {
		SampleBatch sampleBatch = sampleBatchRepository.findById(sampleBatchId)
				.orElseThrow(() -> new NotFoundException("No existe un lote con el id " + sampleBatchId + "."));
		if (!SampleBatchStatus.IN_PROCESS.equals(sampleBatch.getStatus())) {
			throw new BadRequestException("El lote #" + sampleBatchId + " ya tiene cargado los resultados.");
		}
		sampleBatch.setFinalReportsUrl(urlDTO.getUrl());
		sampleBatch = sampleBatchRepository.save(sampleBatch);

		List<Sample> failedSamples = sampleBatch.getSamples().stream()
				.filter(s -> urlDTO.getFailedSamples().contains(s.getId())).collect(Collectors.toList());
		List<Sample> successfulSamples = sampleBatch.getSamples().stream()
				.filter(s -> !urlDTO.getFailedSamples().contains(s.getId())).collect(Collectors.toList());

		failedSamples.forEach(sample -> {
			sample.setFailed(true);
			sample.getStudy().setSample(null);
			studyService.addCheckpointWithLoggedUser(StudyStatus.ESPERANDO_SELECCION_DE_TURNO, sample.getStudy());
			studyService.saveStudy(sample.getStudy());
		});
		successfulSamples.forEach(sample -> {
			sample.setFailed(false);
			studyService.addCheckpointWithLoggedUser(StudyStatus.ESPERANDO_INTERPRETACION_DE_RESULTADOS, sample.getStudy());
			studyService.saveStudy(sample.getStudy());
		});
		sampleBatch.setStatus(SampleBatchStatus.PROCESSED);
		sampleBatchRepository.save(sampleBatch);
		return null;
	}

}
