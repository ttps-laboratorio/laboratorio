package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.request.StudyDTO;
import com.ttps.laboratorio.entity.Checkpoint;
import com.ttps.laboratorio.entity.Employee;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.entity.User;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IStudyRepository;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StudyService {

	private final IStudyRepository studyRepository;

	private final PatientService patientService;

	private final UserService userService;

	private final EmployeeService employeeService;

	private final StudyStatusService studyStatusService;

	private final DoctorService doctorService;

	private final StudyTypeService studyTypeService;

	private final PresumptiveDiagnosisService presumptiveDiagnosisService;

	private final FileDownloadService fileDownloadService;

	public StudyService(IStudyRepository studyRepository,
											PatientService patientService,
											UserService userService,
											EmployeeService employeeService,
											StudyStatusService studyStatusService,
											DoctorService doctorService,
											StudyTypeService studyTypeService,
											PresumptiveDiagnosisService presumptiveDiagnosisService,
											FileDownloadService fileDownloadService) {
		this.studyRepository = studyRepository;
		this.patientService = patientService;
		this.userService = userService;
		this.employeeService = employeeService;
		this.studyStatusService = studyStatusService;
		this.doctorService = doctorService;
		this.studyTypeService = studyTypeService;
		this.presumptiveDiagnosisService = presumptiveDiagnosisService;
		this.fileDownloadService = fileDownloadService;
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
	public List<Study> getAllStudies() {
		return new ArrayList<>(studyRepository.findAll());
	}

	public Study createStudy(Long patientId, StudyDTO request) {
		Study study = new Study();
		Patient patient = patientService.getPatient(patientId);
		patient.addStudy(study);
		study.setBudget(request.getBudget());
		study.setExtractionAmount(request.getExtractionAmount());
		study.setReferringDoctor(doctorService.getDoctor(request.getReferringDoctorId().longValue()));
		study.setType(studyTypeService.getStudyType(request.getStudyTypeId().longValue()));
		study.setPresumptiveDiagnosis(presumptiveDiagnosisService.getPresumptiveDiagnosis(request.getPresumptiveDiagnosisId().longValue()));
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User queriedUser = userService.getUserByUsername(username);
		Employee employee = employeeService.getByUser(queriedUser);
		Checkpoint checkpoint = new Checkpoint();
		checkpoint.setStudy(study);
		checkpoint.setCreatedBy(employee);
		checkpoint.setStatus(studyStatusService.getStudyStatus(1L));
		study.getCheckpoints().add(checkpoint);
		return studyRepository.save(study);
	}

	public void updateStudy(Long studyId, StudyDTO request) {
		Study study = studyRepository.findById(studyId)
				.orElseThrow(() -> new NotFoundException("No existe un estudio con el id " + studyId + "."));
		StudyStatus actualStatus = study.getActualStatus();
		if (actualStatus.getOrder() == 1) {
			study.setBudget(request.getBudget());
		} else {
			throw new BadRequestException("El paciente ya abono el presupuesto.");
		}
		if (!study.getPaidExtractionAmount()) {
			study.setExtractionAmount(request.getExtractionAmount());
		} else {
			throw new BadRequestException("El laboratorio ya abono el monto extraccionista.");
		}
		if (actualStatus.getOrder() <= 2) {
			study.setType(studyTypeService.getStudyType(request.getStudyTypeId().longValue()));
		} else {
			throw new BadRequestException("Ya se envio el consentimiento informado al paciente.");
		}
		study.setReferringDoctor(doctorService.getDoctor(request.getReferringDoctorId().longValue()));
		study.setPresumptiveDiagnosis(presumptiveDiagnosisService.getPresumptiveDiagnosis(request.getPresumptiveDiagnosisId().longValue()));
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

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				Checkpoint checkpoint = new Checkpoint();
				checkpoint.setStudy(study);
				checkpoint.setCreatedBy(null);
				checkpoint.setStatus(studyStatusService.getStudyStatus(12L));
				study.getCheckpoints().add(checkpoint);
			}
		}, 30L * 24 * 60 * 60 * 1000);
	}

}