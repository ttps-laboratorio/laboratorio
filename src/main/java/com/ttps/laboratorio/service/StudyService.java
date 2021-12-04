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
import java.util.ArrayList;
import java.util.List;
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

	public StudyService(IStudyRepository studyRepository,
											PatientService patientService,
											UserService userService,
											EmployeeService employeeService,
											StudyStatusService studyStatusService,
											DoctorService doctorService,
											StudyTypeService studyTypeService,
											PresumptiveDiagnosisService presumptiveDiagnosisService) {
		this.studyRepository = studyRepository;
		this.patientService = patientService;
		this.userService = userService;
		this.employeeService = employeeService;
		this.studyStatusService = studyStatusService;
		this.doctorService = doctorService;
		this.studyTypeService = studyTypeService;
		this.presumptiveDiagnosisService = presumptiveDiagnosisService;
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
//		User currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

}