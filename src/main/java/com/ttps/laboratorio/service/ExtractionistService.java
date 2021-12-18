package com.ttps.laboratorio.service;

import com.ttps.laboratorio.entity.Checkpoint;
import com.ttps.laboratorio.entity.Extractionist;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IExtractionistRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

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

	public Study setExtractionistById(Long studyId, Long extractionistId) {
		Study study = studyService.getStudy(studyId);
		if (study.getActualStatus() != null && !study.getActualStatus().getId().equals(6L)) {
			throw new BadRequestException(
					"El estudio no se encuentra en el estado correspondiente para seleccionar al extraccionista.");
		}
		study.setExtractionist(getExtractionist(extractionistId));
		studyService.setCheckpointWithStatus(7L, study);
		StudyStatus statusWaitingForPayment = studyStatusService.getStudyStatus(7L);
		List<Study> studiesReadyForProcess = studyService.getStudiesByActualStatus(statusWaitingForPayment);
		if (studiesReadyForProcess != null && studiesReadyForProcess.size() == 10) {
			studiesReadyForProcess.forEach(s -> {
				Checkpoint checkpoint = new Checkpoint();
				checkpoint.setStudy(s);
				checkpoint.setCreatedBy(null);
				checkpoint.setStatus(studyStatusService.getStudyStatus(8L));
				s.getCheckpoints().add(checkpoint);
				studyService.saveStudy(s);
			});
			sampleBatchService.createBatch(studiesReadyForProcess);
		}
		return studyService.saveStudy(study);
	}
}
