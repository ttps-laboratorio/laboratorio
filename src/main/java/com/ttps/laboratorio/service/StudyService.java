package com.ttps.laboratorio.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ttps.laboratorio.dto.request.StudyDTO;
import com.ttps.laboratorio.dto.response.StudyItemResponseDTO;
import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.entity.Checkpoint;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.entity.User;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IStudyRepository;

@Service
public class StudyService {

	private final IStudyRepository studyRepository;

	private final PatientService patientService;

	private final UserService userService;

	private final StudyStatusService studyStatusService;

	private final DoctorService doctorService;

	private final StudyTypeService studyTypeService;

	private final PresumptiveDiagnosisService presumptiveDiagnosisService;

	private final FileDownloadService fileDownloadService;

	private final ModelMapper mapper;

	private final ExtractionistService extractionistService;

	public StudyService(IStudyRepository studyRepository,
											PatientService patientService,
											UserService userService,
											StudyStatusService studyStatusService,
											DoctorService doctorService,
											StudyTypeService studyTypeService,
											PresumptiveDiagnosisService presumptiveDiagnosisService,
											FileDownloadService fileDownloadService,
											ExtractionistService extractionistService) {
		this.studyRepository = studyRepository;
		this.patientService = patientService;
		this.userService = userService;
		this.studyStatusService = studyStatusService;
		this.doctorService = doctorService;
		this.studyTypeService = studyTypeService;
		this.presumptiveDiagnosisService = presumptiveDiagnosisService;
		this.fileDownloadService = fileDownloadService;
		this.mapper = new ModelMapper();
		this.mapper.getConfiguration().setSkipNullEnabled(true);
		this.extractionistService = extractionistService;
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
		})
				.collect(Collectors.toList());
	}

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
		return studyRepository.save(study);
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

	public void downloadBudgetFile(Long id, HttpServletResponse response) throws IOException {
		Study study = studyRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No existe un estudio con el id " + id + "."));
		if (study.getActualStatus().getId() != 1L) {
			if (study.getActualStatus().getId() == 12L) {
				throw new BadRequestException("El estudio con id " + id + " fue anulado. Deberá crear un nuevo estudio.");
			} else {
				throw new BadRequestException("El presupuesto del estudio con id " + id + " ya fue abonado.");
			}
		}
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue =
				"attachment; filename=" + study.getPatient().getLastName() + study.getPatient().getFirstName() + currentDateTime +
						".pdf";
		response.setHeader(headerKey, headerValue);

		fileDownloadService.exportBudget(response, study);
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
				.filter(s -> s.getRecentCheckpoint().getCreatedAt().plusDays(30).compareTo(LocalDateTime.now()) < 0).forEach(study -> {
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
