package com.ttps.laboratorio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttps.laboratorio.entity.Extractionist;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IExtractionistRepository;

@Service
public class ExtractionistService {

	private final IExtractionistRepository extractionistRepository;

	private final StudyService studyService;

	private final StudyStatusService studyStatusService;

	private final SampleBatchService sampleBatchService;

	public ExtractionistService(IExtractionistRepository extractionistRepository, StudyService studyService,
															StudyStatusService studyStatusService, SampleBatchService sampleBatchService) {
		this.extractionistRepository = extractionistRepository;
		this.studyService = studyService;
		this.studyStatusService = studyStatusService;
		this.sampleBatchService = sampleBatchService;
	}

	public Extractionist getExtractionist(Long extractionistId) {
		return this.extractionistRepository.findById(extractionistId)
				.orElseThrow(() -> new NotFoundException("No existe un extracionista con el id " + extractionistId + "."));
	}

	public List<Extractionist> getAllExtractionists() {
		return new ArrayList<>(extractionistRepository.findAll());
	}

	@Transactional
	public void setExtractionistById(Long studyId, Long extractionistId) {
		Study study = studyService.getStudy(studyId);
		if (study.getActualStatus() != null && !study.getActualStatus().getId().equals(StudyStatus.ESPERANDO_RETIRO_DE_MUESTRA)) {
			throw new BadRequestException("El estudio #" + studyId + " no se encuentra en el estado correspondiente para seleccionar al extraccionista.");
		}
		study.setExtractionist(getExtractionist(extractionistId));
		studyService.addCheckpointWithLoggedUser(StudyStatus.ESPERANDO_LOTE_DE_MUESTRA_PARA_INICIAR_PROCESAMIENTO,
				study);
		studyService.saveFlushStudy(study);
		StudyStatus statusWaitingForPayment = studyStatusService.getStudyStatus(StudyStatus.ESPERANDO_LOTE_DE_MUESTRA_PARA_INICIAR_PROCESAMIENTO);
		List<Study> studiesReadyForProcess = studyService.getStudiesByActualStatus(statusWaitingForPayment);
		if (studiesReadyForProcess != null && studiesReadyForProcess.size() == SampleBatchService.SAMPLE_BATCH_COUNT) {
			studiesReadyForProcess.forEach(s -> {
				// we register the user that creates the batch
				studyService.addCheckpointWithLoggedUser(StudyStatus.ESPERANDO_RESULTADO_BIOTECNOLOGICO, s);
				studyService.saveStudy(s);
			});
			sampleBatchService.createBatch(studiesReadyForProcess);
		}
		studyService.saveStudy(study);
	}

}
