package com.ttps.laboratorio.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ttps.laboratorio.dto.request.StudyDTO;
import com.ttps.laboratorio.dto.request.StudySearchFilterDTO;
import com.ttps.laboratorio.dto.request.UnpaidStudiesDTO;
import com.ttps.laboratorio.dto.response.PatientResponseDTO;
import com.ttps.laboratorio.dto.response.SampleBatchResponseDTO;
import com.ttps.laboratorio.dto.response.SampleResponseDTO;
import com.ttps.laboratorio.dto.response.StudyItemResponseDTO;
import com.ttps.laboratorio.dto.response.StudyResponseDTO;
import com.ttps.laboratorio.dto.response.StudyStatusResponseDTO;
import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.entity.Checkpoint;
import com.ttps.laboratorio.entity.Extractionist;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.entity.RoleEnum;
import com.ttps.laboratorio.entity.Sample;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.entity.StudyType;
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

	private final LaboratoryFileUtils laboratoryFileUtils;

	public StudyService(IStudyRepository studyRepository, PatientService patientService, UserService userService,
			StudyStatusService studyStatusService, DoctorService doctorService, StudyTypeService studyTypeService,
			PresumptiveDiagnosisService presumptiveDiagnosisService, PdfGeneratorService pdfGeneratorService,
			LaboratoryFileUtils fileNameUtils) {
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
		this.laboratoryFileUtils = fileNameUtils;
	}

	public StudyResponseDTO getStudy(Long studyId) {
		return createStudyResponseDTO(getStudyById(studyId));
	}

	public Study getStudyById(Long studyId) {
		return this.studyRepository.findById(studyId)
				.orElseThrow(() -> new NotFoundException("No existe un estudio #" + studyId + "."));
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
		User user = userService.getLoggedUser();
		List<StudyItemResponseDTO> studies = new ArrayList<>();
		if (RoleEnum.EMPLOYEE.equals(user.getRole())) {
			studies = studyRepository.findAll(StudySpecifications.all(filter)).stream().map(s -> {
				StudyItemResponseDTO item = this.mapper.map(s, StudyItemResponseDTO.class);
				item.setFirstName(s.getPatient().getFirstName());
				item.setLastName(s.getPatient().getLastName());
				return item;
			}).collect(Collectors.toList());
		} else if (RoleEnum.PATIENT.equals(user.getRole())) {
			studies = getAllPatientStudies(patientService.getByUser(user).getId());
		}
		return studies;
	}

	@Transactional(rollbackFor = { LaboratoryException.class, Exception.class })
	public StudyResponseDTO createStudy(Long patientId, StudyDTO request) {
		Study study = new Study();
		Patient patient = patientService.getPatient(patientId);
		patient.addStudy(study);
		study.setBudget(request.getBudget());
		study.setExtractionAmount(request.getExtractionAmount());
		study.setReferringDoctor(doctorService.getDoctor(request.getReferringDoctor().getId()));
		study.setType(studyTypeService.getStudyType(request.getStudyType().getId()));
		study.setPresumptiveDiagnosis(
				presumptiveDiagnosisService.getPresumptiveDiagnosis(request.getPresumptiveDiagnosis().getId()));
		addCheckpointWithLoggedUser(StudyStatus.ESPERANDO_COMPROBANTE_DE_PAGO, study);
		study = studyRepository.save(study);
		try {
			String filename = laboratoryFileUtils.getFilenameBudget(study.getPatient().getId(), study.getId());
			pdfGeneratorService.generateBudget(study, filename);
		} catch (IOException e) {
			log.error("No se pudo generar el pdf del presupuesto del paciente [dni: " + patient.getDni() + "]", e);
			throw new LaboratoryException("No se pudo generar el presupuesto del paciente #" + patientId);
		}
		try {
			String filename = laboratoryFileUtils.getFilenameConsent(study.getPatient().getId(), study.getId());
			pdfGeneratorService.generateConsent(study, filename);
		} catch (IOException e) {
			log.error("No se pudo generar el pdf del consentimiento del paciente [dni: " + patient.getDni() + "]", e);
			throw new LaboratoryException("No se pudo generar el consentimiento del paciente #" + patientId);
		}
		return createStudyResponseDTO(study);
	}

	public StudyResponseDTO updateStudy(Long studyId, StudyDTO studyDTO) {
		Study study = studyRepository.findById(studyId)
				.orElseThrow(() -> new NotFoundException("No existe un estudio #" + studyId + "."));
		StudyStatus actualStatus = study.getActualStatus();
		if (actualStatus.getId().equals(StudyStatus.ESPERANDO_COMPROBANTE_DE_PAGO)) {
			study.setBudget(studyDTO.getBudget());
		} else {
			throw new BadRequestException("El paciente ya abono el presupuesto.");
		}
		if (!study.getPaidExtractionAmount()) {
			study.setExtractionAmount(studyDTO.getExtractionAmount());
		} else {
			throw new BadRequestException("El laboratorio ya abono el monto extraccionista.");
		}
		if (actualStatus.getId().intValue() <= StudyStatus.ENVIAR_CONSENTIMIENTO_INFORMADO) {
			study.setType(studyTypeService.getStudyType(studyDTO.getStudyType().getId()));
		} else {
			throw new BadRequestException("Ya se envio el consentimiento informado al paciente.");
		}
		study.setReferringDoctor(doctorService.getDoctor(studyDTO.getReferringDoctor().getId()));
		study.setPresumptiveDiagnosis(
				presumptiveDiagnosisService.getPresumptiveDiagnosis(studyDTO.getPresumptiveDiagnosis().getId()));
		return createStudyResponseDTO(study);
	}

	@Transactional(rollbackFor = { LaboratoryException.class, Exception.class })
	public Study confirmPayment(Long studyId, boolean confirm) {
		Study study = studyRepository.findById(studyId)
				.orElseThrow(() -> new NotFoundException("No existe un estudio #" + studyId + "."));
		StudyStatus actualStatus = study.getActualStatus();

		if (actualStatus.getId().equals(StudyStatus.ANULADO)) {
			throw new BadRequestException("El estudio #" + studyId + " fue anulado. Deber?? crear un nuevo estudio.");
		}

		// if actual status is before ESPERANDO_VALIDACION_COMPROBANTE_DE_PAGO can't
		// accept or cancel the payment proof
		if (actualStatus.getId().intValue() < StudyStatus.ESPERANDO_VALIDACION_COMPROBANTE_DE_PAGO.intValue()) {
			throw new BadRequestException("A??n no se ha subido el comprobante de pago del estudio #" + studyId);
		}

		// if actual status is after throw operation not permitted
		if (actualStatus.getId().intValue() > StudyStatus.ESPERANDO_VALIDACION_COMPROBANTE_DE_PAGO.intValue()) {
			throw new BadRequestException("Operaci??n no permitida para el estudio #" + studyId);
		}
		// from here actual status is ESPERANDO_VALIDACION_COMPROBANTE_DE_PAGO

		// if confirm valid payment set status to ENVIAR_CONSENTIMIENTO_INFORMADO
		// else change status to back (ESPERANDO_COMPROBANTE_DE_PAGO)
		Long studyStatus = confirm ? StudyStatus.ENVIAR_CONSENTIMIENTO_INFORMADO
				: StudyStatus.ESPERANDO_COMPROBANTE_DE_PAGO;
		addCheckpointWithLoggedUser(studyStatus, study);
		return studyRepository.save(study);
	}

	public Resource downloadBudgetFile(Long studyId) {
		Study study = studyRepository.findById(studyId)
				.orElseThrow(() -> new NotFoundException("No existe un estudio #" + studyId + "."));

		// check if user is a patient and has permission
		patientService.validateLoggedPatient(study.getPatient().getId());

		StudyStatus actualStatus = study.getActualStatus();
		if (actualStatus.getId().equals(StudyStatus.ANULADO)) {
			throw new BadRequestException("El estudio #" + studyId + " fue anulado. Deber?? crear un nuevo estudio.");
		}

		String filename = laboratoryFileUtils.getFilenameBudget(study.getPatient().getId(), study.getId());
		File file = new File(filename);
		if (!file.exists()) {
			throw new LaboratoryException("No existe el presupuesto del estudio #" + studyId);
		}
		return new FileSystemResource(file);
	}

	public Resource downloadPaymentProofFile(Long studyId) {
		Study study = studyRepository.findById(studyId)
				.orElseThrow(() -> new NotFoundException("No existe un estudio #" + studyId + "."));

		// check if user is a patient and has permission
		patientService.validateLoggedPatient(study.getPatient().getId());

		StudyStatus actualStatus = study.getActualStatus();
		if (actualStatus.getId().equals(StudyStatus.ANULADO)) {
			throw new BadRequestException("El estudio #" + studyId + " fue anulado. Deber?? crear un nuevo estudio.");
		}

		if (actualStatus.getId().intValue() < StudyStatus.ESPERANDO_VALIDACION_COMPROBANTE_DE_PAGO) {
			throw new BadRequestException("El estudio #" + studyId + " no tiene comprobante de pago");
		}

		String filename = laboratoryFileUtils.getFilenamePaymentProof(study.getPatient().getId(), study.getId());
		File file = new File(filename);
		if (!file.exists()) {
			throw new LaboratoryException("No existe el documento de consentimiento del estudio #" + studyId);
		}
		return new FileSystemResource(file);
	}

	public Resource downloadConsentFile(Long studyId) {
		Study study = studyRepository.findById(studyId)
				.orElseThrow(() -> new NotFoundException("No existe un estudio #" + studyId + "."));

		// check if user is a patient and has permission
		patientService.validateLoggedPatient(study.getPatient().getId());

		StudyStatus actualStatus = study.getActualStatus();
		if (actualStatus.getId().equals(StudyStatus.ANULADO)) {
			throw new BadRequestException("El estudio #" + studyId + " fue anulado. Deber?? crear un nuevo estudio.");
		}

		if (actualStatus.getId().intValue() < StudyStatus.ENVIAR_CONSENTIMIENTO_INFORMADO.intValue()) {
			throw new BadRequestException("El estudio #" + studyId + " a??n no fue abonado.");
		}
		// from here the study should have a consent file

		String filename = laboratoryFileUtils.getFilenameConsent(study.getPatient().getId(), study.getId());
		File file = new File(filename);
		if (!file.exists()) {
			throw new LaboratoryException("No existe el documento de consentimiento del estudio #" + studyId);
		}

		// Change state only if actual state is Enviar consentimiento informado
		if (study.getActualStatus().getId().equals(StudyStatus.ENVIAR_CONSENTIMIENTO_INFORMADO)) {
			addCheckpointWithLoggedUser(StudyStatus.ESPERANDO_CONSENTIMIENTO_INFORMADO_FIRMADO, study);
			study = studyRepository.save(study);
		}
		return new FileSystemResource(file);
	}

	public Resource downloadSignedConsentFile(Long studyId) {
		Study study = studyRepository.findById(studyId)
				.orElseThrow(() -> new NotFoundException("No existe un estudio #" + studyId + "."));

		// check if user is a patient and has permission
		patientService.validateLoggedPatient(study.getPatient().getId());

		StudyStatus actualStatus = study.getActualStatus();
		if (actualStatus.getId().equals(StudyStatus.ANULADO)) {
			throw new BadRequestException("El estudio #" + studyId + " fue anulado. Deber?? crear un nuevo estudio.");
		}

		if (actualStatus.getId().intValue() <= StudyStatus.ESPERANDO_CONSENTIMIENTO_INFORMADO_FIRMADO.intValue()) {
			throw new BadRequestException("El estudio #" + studyId + " a??n no tiene consentimiento informado firmado.");
		}
		// from here the study should have a signed consent file

		String filename = laboratoryFileUtils.getFilenameSignedConsent(study.getPatient().getId(), study.getId());
		File file = new File(filename);
		if (!file.exists()) {
			throw new LaboratoryException("No existe el documento de consentimiento firmado del estudio #" + studyId);
		}
		return new FileSystemResource(file);
	}

	public Resource downloadFinalReportFile(Long studyId) {
		Study study = getStudyById(studyId);

		// check if user is a patient and has permission
		patientService.validateLoggedPatient(study.getPatient().getId());

		StudyStatus actualStatus = study.getActualStatus();
		if (actualStatus.getId().equals(StudyStatus.ANULADO)) {
			throw new BadRequestException("El estudio #" + studyId + " fue anulado. Deber?? crear un nuevo estudio.");
		}

		if (actualStatus.getId().intValue() < StudyStatus.ESPERANDO_SER_ENTREGADO_A_MEDICO_DERIVANTE.intValue()) {
			throw new BadRequestException("El estudio #" + studyId + " a??n no tiene generado el resultado final.");
		}
		// from here the study should have a final report file

		String filename = laboratoryFileUtils.getFilenameFinalReport(study.getPatient().getId(), study.getId());
		File file = new File(filename);
		if (!file.exists()) {
			throw new LaboratoryException("No existe el documento de reporte final del estudio #" + studyId);
		}

		// Change state only if actual state is Esperando ser entregado a medico
		// derivante
		if (study.getActualStatus().getId().equals(StudyStatus.ESPERANDO_SER_ENTREGADO_A_MEDICO_DERIVANTE)) {
			addCheckpointWithLoggedUser(StudyStatus.RESULTADO_ENTREGADO, study);
			studyRepository.save(study);
		}
		return new FileSystemResource(file);
	}

	@Transactional(rollbackFor = { LaboratoryException.class, Exception.class })
	public StudyResponseDTO uploadPaymentProofFile(Long studyId, MultipartFile paymentProofPdf) {
		Study study = studyRepository.findById(studyId)
				.orElseThrow(() -> new NotFoundException("No existe un estudio #" + studyId + "."));

		// check if user is a patient and has permission
		patientService.validateLoggedPatient(study.getPatient().getId());

		StudyStatus actualStatus = study.getActualStatus();
		if (actualStatus.getId().equals(StudyStatus.ANULADO)) {
			throw new BadRequestException("El estudio #" + studyId + " fue anulado. Deber?? crear un nuevo estudio.");
		}

		if (actualStatus.getId().intValue() != StudyStatus.ESPERANDO_COMPROBANTE_DE_PAGO.intValue()) {
			throw new BadRequestException(
					"No se puede subir el comprobante de pago. Operaci??n no permitida para el estudio #" + studyId);
		}

		try {
			String filename = laboratoryFileUtils.getFilenamePaymentProof(study.getPatient().getId(), study.getId());
			File file = new File(filename);
			Files.copy(paymentProofPdf.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			addCheckpointWithLoggedUser(StudyStatus.ESPERANDO_VALIDACION_COMPROBANTE_DE_PAGO, study);
			study = studyRepository.save(study);
		} catch (IOException e) {
			throw new LaboratoryException(
					"Error al guardar el documento de consentimiento informado firmado del estudio con id: " + studyId);
		}
		return createStudyResponseDTO(study);
	}

	@Transactional(rollbackFor = { LaboratoryException.class, Exception.class })
	public StudyResponseDTO uploadSignedConsentFile(Long studyId, MultipartFile signedConsentPdf) {
		Study study = studyRepository.findById(studyId)
				.orElseThrow(() -> new NotFoundException("No existe un estudio #" + studyId + "."));

		// check if user is a patient and has permission
		patientService.validateLoggedPatient(study.getPatient().getId());

		StudyStatus actualStatus = study.getActualStatus();
		if (actualStatus.getId().equals(StudyStatus.ANULADO)) {
			throw new BadRequestException("El estudio #" + studyId + " fue anulado. Deber?? crear un nuevo estudio.");
		}

		if (actualStatus.getId().intValue() != StudyStatus.ESPERANDO_CONSENTIMIENTO_INFORMADO_FIRMADO.intValue()) {
			throw new BadRequestException(
					"No se puede subir el consentimiento informado firmado. Operaci??n no permitida para el estudio #"
							+ studyId);
		}

		try {
			String filename = laboratoryFileUtils.getFilenameSignedConsent(study.getPatient().getId(), study.getId());
			File file = new File(filename);
			Files.copy(signedConsentPdf.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			addCheckpointWithLoggedUser(StudyStatus.ESPERANDO_SELECCION_DE_TURNO, study);
			study = studyRepository.save(study);
		} catch (IOException e) {
			throw new LaboratoryException(
					"Error al guardar el documento de consentimiento informado firmado del estudio con id: " + studyId);
		}
		return createStudyResponseDTO(study);
	}

	public Study getStudyByAppointment(Appointment appointment) {
		return studyRepository.findByAppointment(appointment);
	}

	public Study saveStudy(Study study) {
		return studyRepository.save(study);
	}

	public Study saveFlushStudy(Study study) {
		return studyRepository.saveAndFlush(study);
	}

	/**
	 * Creates a checkpoint with params and adds to study checkpoints collection
	 *
	 * @param study  Owner of the checkpoint
	 * @param status Id of status of study for checkpoint
	 * @param user   User that performs the operation or null
	 */
	public void addNewCheckpoint(Study study, Long status, User user) {
		StudyStatus studyStatus = studyStatusService.getStudyStatus(status);
		Checkpoint checkpoint = Checkpoint.builder().status(studyStatus).createdBy(user).build();
		study.addCheckpoint(checkpoint);
	}

	/**
	 * Creates a checkpoint with params for authenticated user and adds to study
	 * checkpoints collection
	 *
	 * @param status
	 * @param study
	 */
	public void addCheckpointWithLoggedUser(Long status, Study study) {
		User user = userService.getLoggedUser();
		addNewCheckpoint(study, status, user);
	}

	public List<Study> getStudiesByActualStatus(Long studyStatus) {
		return studyRepository.findAll(StudySpecifications.withStatus(studyStatus));
	}

	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional
	public void cancelStudy() {
		getStudiesByActualStatus(StudyStatus.ESPERANDO_COMPROBANTE_DE_PAGO).stream()
				.filter(s -> s.getRecentCheckpoint().getCreatedAt().plusDays(30).compareTo(LocalDateTime.now()) < 0)
				.forEach(study -> {
					StudyStatus studyStatus = studyStatusService.getStudyStatus(StudyStatus.ANULADO);
					Checkpoint checkpoint = Checkpoint.builder().status(studyStatus).createdBy(null).build();
					study.addCheckpoint(checkpoint);
					studyRepository.save(study);
				});
	}

	public Study getStudyBySample(Sample sample) {
		return studyRepository.findBySample(sample).orElseThrow(
				() -> new BadRequestException("No existe un estudio para la muestra con id " + sample.getId() + "."));
	}

	public List<StudyItemResponseDTO> getAllPatientStudies(Long patientId) {
		patientService.validateLoggedPatient(patientId);
		return studyRepository.findByPatient(patientService.getPatient(patientId)).stream().map(s -> {
			StudyItemResponseDTO item = this.mapper.map(s, StudyItemResponseDTO.class);
			item.setActualStatus(this.mapper.map(getStatusByRole(s), StudyStatusResponseDTO.class));
			item.setFirstName(s.getPatient().getFirstName());
			item.setLastName(s.getPatient().getLastName());
			return item;
		}).collect(Collectors.toList());
	}

	public List<StudyItemResponseDTO> getAllUnpaidStudies() {
		return studyRepository.findByPaidExtractionAmountFalse().stream().map(s -> {
			StudyItemResponseDTO item = this.mapper.map(s, StudyItemResponseDTO.class);
			item.setFirstName(s.getPatient().getFirstName());
			item.setLastName(s.getPatient().getLastName());
			return item;
		}).collect(Collectors.toList());
	}

	@Transactional
	public void payExtractionAmountStudies(UnpaidStudiesDTO unpaidStudiesDTO) {
		unpaidStudiesDTO.getUnpaidStudies().forEach(studyId -> {
			Study study = getStudyById(studyId);
			study.setPaidExtractionAmount(true);
			studyRepository.save(study);
		});
	}

	public Integer studiesByStudyType(StudyType studyType) {
		return studyRepository.findByType(studyType).size();
	}

	public Integer studiesByMonthOfYear(Integer month, Integer year) {
		LocalDateTime from = LocalDateTime.of(year, month, 1, 0, 0);
		LocalDateTime to = from.plusMonths(1);
		return studyRepository.findByCreatedAtBetween(from, to).size();
	}

	public List<Integer> yearsWithStudies() {
		return studyRepository.findAll().stream().map(s -> s.getCreatedAt().getYear()).distinct().sorted()
				.collect(Collectors.toList());
	}

	public Integer studiesByStudyStatus(StudyStatus studyStatus) {
		return (int) studyRepository.findAll().stream().filter(study -> studyStatus.equals(study.getActualStatus()))
				.count();
	}

	private StudyResponseDTO createStudyResponseDTO(Study study) {
		patientService.validateLoggedPatient(study.getPatient().getId());
		PatientResponseDTO patientDTO = new PatientResponseDTO(study.getPatient().getId(), study.getPatient().getDni(),
				study.getPatient().getFirstName(), study.getPatient().getLastName(), study.getPatient().getBirthDate());

		return new StudyResponseDTO(study.getId(), study.getCreatedAt(), study.getBudget(), study.getExtractionAmount(),
				study.getPaidExtractionAmount(), patientDTO, study.getAppointment(), study.getReferringDoctor(),
				study.getType(), study.getPresumptiveDiagnosis(), getStatusByRole(study), getSampleByRole(study),
				getExtractionistByRole(study));
	}

	private Extractionist getExtractionistByRole(Study study) {
		Extractionist extractionist = study.getExtractionist();
		User user = userService.getLoggedUser();
		if (RoleEnum.PATIENT.equals(user.getRole())) {
			extractionist = null;
		}
		return extractionist;
	}

	private SampleResponseDTO getSampleByRole(Study study) {
		SampleResponseDTO sample = null;
		if (study.getSample() != null) {
			SampleBatchResponseDTO sampleBatch = null;
			if (study.getSample().getSampleBatch() != null) {
				sampleBatch = new SampleBatchResponseDTO(study.getSample().getSampleBatch().getId(),
						study.getSample().getSampleBatch().getStatus(),
						study.getSample().getSampleBatch().getFinalReportsUrl());
			}
			sample = new SampleResponseDTO(study.getSample().getId(), study.getSample().getMilliliters(),
					study.getSample().getFreezer(), study.getSample().getFailed(), study.getId(), sampleBatch);
			User user = userService.getLoggedUser();
			if (RoleEnum.PATIENT.equals(user.getRole())) {
				sample.setFreezer(null);
				if (sample.getSampleBatch() != null) {
					sample.getSampleBatch().setFinalReportsUrl(null);
				}
			}
		}
		return sample;
	}

	private StudyStatus getStatusByRole(Study study) {
		StudyStatus actualStatus = study.getActualStatus();
		User user = userService.getLoggedUser();
		if (RoleEnum.PATIENT.equals(user.getRole())) {
			if (study.getActualStatus().getId() >= StudyStatus.ESPERANDO_RETIRO_DE_MUESTRA
					&& study.getActualStatus().getId() <= StudyStatus.ESPERANDO_SER_ENTREGADO_A_MEDICO_DERIVANTE) {
				actualStatus = studyStatusService.getStudyStatus(StudyStatus.ESPERANDO_RESULTADO);
			} else if (StudyStatus.RESULTADO_ENTREGADO.equals(study.getActualStatus().getId())) {
				actualStatus = studyStatusService.getStudyStatus(StudyStatus.RESULTADO_COMPLETO);
			}
		}
		return actualStatus;
	}

}
