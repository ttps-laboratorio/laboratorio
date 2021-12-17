package com.ttps.laboratorio.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttps.laboratorio.dto.request.StudyDTO;
import com.ttps.laboratorio.dto.request.StudySearchFilterDTO;
import com.ttps.laboratorio.dto.response.StudyItemResponseDTO;
import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.entity.Checkpoint;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.entity.User;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.LaboratoryException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IStudyRepository;
import com.ttps.laboratorio.repository.specification.StudySpecifications;
import com.ttps.laboratorio.utils.LaboratoryFileUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudyService {

	private final IStudyRepository studyRepository;

	private final PatientService patientService;

	private final UserService userService;

	private final StudyStatusService studyStatusService;

	private final DoctorService doctorService;

	private final StudyTypeService studyTypeService;

	private final PresumptiveDiagnosisService presumptiveDiagnosisService;

	private final PdfGeneratorService pdfGeneratorService;

	private final ModelMapper mapper;

	private final ExtractionistService extractionistService;

	private final LaboratoryFileUtils laboratoryFileUtils;

	public StudyService(IStudyRepository studyRepository, PatientService patientService, UserService userService,
			StudyStatusService studyStatusService, DoctorService doctorService, StudyTypeService studyTypeService,
			PresumptiveDiagnosisService presumptiveDiagnosisService, PdfGeneratorService pdfGeneratorService,
			ExtractionistService extractionistService, LaboratoryFileUtils fileNameUtils) {
		this.studyRepository = studyRepository;
		this.patientService = patientService;
		this.userService = userService;
		this.studyStatusService = studyStatusService;
		this.doctorService = doctorService;
		this.studyTypeService = studyTypeService;
		this.presumptiveDiagnosisService = presumptiveDiagnosisService;
		this.pdfGeneratorService = pdfGeneratorService;
		this.mapper = new ModelMapper();
		this.mapper.getConfiguration().setSkipNullEnabled(true);
		this.extractionistService = extractionistService;
		this.laboratoryFileUtils = fileNameUtils;
	}

	public Study getStudy(Long studyId) {
		return this.studyRepository.findById(studyId)
				.orElseThrow(() -> new NotFoundException("No existe un estudio con el id " + studyId + "."));
	}

	/**
	 * Gets all studies registered.
	 *
	 * @return List of all the studies
	 */
	public List<StudyItemResponseDTO> getAllStudies() {
		return studyRepository.findAll().stream().map(s -> {
			StudyItemResponseDTO item = this.mapper.map(s, StudyItemResponseDTO.class);
			item.setFirstName(s.getPatient().getFirstName());
			item.setLastName(s.getPatient().getLastName());
			return item;
		}).collect(Collectors.toList());
	}

	public List<StudyItemResponseDTO> getAllStudies(StudySearchFilterDTO filter) {
		return studyRepository.findAll(StudySpecifications.all(filter)).stream().map(s -> {
			StudyItemResponseDTO item = this.mapper.map(s, StudyItemResponseDTO.class);
			item.setFirstName(s.getPatient().getFirstName());
			item.setLastName(s.getPatient().getLastName());
			return item;
		}).collect(Collectors.toList());
	}

	@Transactional(rollbackFor = { LaboratoryException.class, Exception.class })
	public Study createStudy(Long patientId, StudyDTO request) {
		Study study = new Study();
		Patient patient = patientService.getPatient(patientId);
		patient.addStudy(study);
		study.setBudget(request.getBudget());
		study.setExtractionAmount(request.getExtractionAmount());
		study.setReferringDoctor(doctorService.getDoctor(request.getReferringDoctor().getId()));
		study.setType(studyTypeService.getStudyType(request.getStudyType().getId()));
		study.setPresumptiveDiagnosis(
				presumptiveDiagnosisService.getPresumptiveDiagnosis(request.getPresumptiveDiagnosis().getId()));
		setCheckpointWithStatus(1L, study);
		study = studyRepository.save(study);
		try {
			String filename = laboratoryFileUtils.getFilenameBudget(study.getPatient().getId(), study.getId());
			pdfGeneratorService.generateBudget(study, filename);
		} catch (IOException e) {
			log.error("No se pudo generar el pdf del presupuesto del paciente [dni: " + patient.getDni() + "]", e);
			throw new LaboratoryException("No se pudo generar el presupuesto del paciente");
		}
		return study;
	}

	public void updateStudy(Long studyId, StudyDTO studyDTO) {
		Study study = studyRepository.findById(studyId)
				.orElseThrow(() -> new NotFoundException("No existe un estudio con el id " + studyId + "."));
		StudyStatus actualStatus = study.getActualStatus();
		if (actualStatus.getOrder() == 1) {
			study.setBudget(studyDTO.getBudget());
		} else {
			throw new BadRequestException("El paciente ya abono el presupuesto.");
		}
		if (!study.getPaidExtractionAmount()) {
			study.setExtractionAmount(studyDTO.getExtractionAmount());
		} else {
			throw new BadRequestException("El laboratorio ya abono el monto extraccionista.");
		}
		if (actualStatus.getOrder() <= 2) {
			study.setType(studyTypeService.getStudyType(studyDTO.getStudyType().getId()));
		} else {
			throw new BadRequestException("Ya se envio el consentimiento informado al paciente.");
		}
		study.setReferringDoctor(doctorService.getDoctor(studyDTO.getReferringDoctor().getId()));
		study.setPresumptiveDiagnosis(
				presumptiveDiagnosisService.getPresumptiveDiagnosis(studyDTO.getPresumptiveDiagnosis().getId()));
	}

	public Resource downloadBudgetFile(Long studyId) {
		// org.springframework.security.core.userdetails.User currentUser =
		// (org.springframework.security.core.userdetails.User)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// here we have to check if the user is a PATIENT that the studyId is his own

		// if (currentUser.getAuthorities().stream().anyMatch(a ->
		// a.getAuthority().equals("ROLE_PATIENT"))) {
		// User user = userService.getUserByUsername(currentUser.getUsername());
		// }

		Study study = studyRepository.findById(studyId)
				.orElseThrow(() -> new NotFoundException("No existe un estudio con el id " + studyId + "."));
		if (study.getActualStatus().getId() == 12L) {
			throw new BadRequestException(
					"El estudio con id " + studyId + " fue anulado. Deberá crear un nuevo estudio.");
		}
		String filename = laboratoryFileUtils.getFilenameBudget(study.getPatient().getId(), study.getId());
		File file = new File(filename);
		if (!file.exists())
			throw new LaboratoryException("No existe el presupuesto del estudio con id " + studyId);
		return new FileSystemResource(file);
	}

	public Study getStudyByAppointment(Appointment appointment) {
		return studyRepository.findByAppointment(appointment);
	}

	public void saveStudy(Study study) {
		studyRepository.save(study);
	}

	public void setCheckpointWithStatus(Long status, Study study) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User queriedUser = userService.getUserByUsername(username);
		Checkpoint checkpoint = new Checkpoint();
		checkpoint.setStudy(study);
		checkpoint.setCreatedBy(queriedUser);
		checkpoint.setStatus(studyStatusService.getStudyStatus(status));
		study.getCheckpoints().add(checkpoint);
	}

	public List<Study> getStudiesByActualStatus(StudyStatus studyStatus) {
		return studyRepository.findAllByActualStatus(studyStatus);
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void cancelStudy() {
		StudyStatus statusWaitingForPayment = studyStatusService.getStudyStatus(1L);
		getStudiesByActualStatus(statusWaitingForPayment).stream()
				.filter(s -> s.getRecentCheckpoint().getCreatedAt().plusDays(30).compareTo(LocalDateTime.now()) < 0)
				.forEach(study -> {
					Checkpoint checkpoint = new Checkpoint();
					checkpoint.setStudy(study);
					checkpoint.setCreatedBy(null);
					checkpoint.setStatus(studyStatusService.getStudyStatus(12L));
					study.getCheckpoints().add(checkpoint);
					studyRepository.save(study);
				});
	}

	public Study setExtractionistById(Long studyId, Long extractionistId) {
		Study study = getStudy(studyId);
		if (study.getActualStatus() != null && !study.getActualStatus().getId().equals(6L)) {
			throw new BadRequestException(
					"El estudio no se encuentra en el estado correspondiente para seleccionar al extraccionista.");
		}
		study.setExtractionist(extractionistService.getExtractionist(extractionistId));
		setCheckpointWithStatus(7L, study);
		return studyRepository.save(study);
	}

}
