package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.StudyDTO;
import com.ttps.laboratorio.entity.Checkpoint;
import com.ttps.laboratorio.entity.Employee;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.entity.User;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IStudyRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StudyService {

  private final IStudyRepository studyRepository;
  private final PatientService patientService;
  private final UserService userService;
  private final EmployeeService employeeService;
  private final StudyStatusService studyStatusService;

  public StudyService (IStudyRepository studyRepository,
                       PatientService patientService,
                       UserService userService,
                       EmployeeService employeeService,
                       StudyStatusService studyStatusService) {
    this.studyRepository = studyRepository;
    this.patientService = patientService;
    this.userService = userService;
    this.employeeService = employeeService;
    this.studyStatusService = studyStatusService;
  }

  public Study getStudy(Long studyId) {
    return this.studyRepository.findById(studyId).orElseThrow(() -> new NotFoundException("No existe un estudio con el id " + studyId + "."));
  }

  /**
   * Gets all studies registered.
   * @return List of all studies
   */
  public List<Study> getAllStudies() {
    return new ArrayList<>(studyRepository.findAll());
  }

  public void createStudy(Long patientId, StudyDTO request) {
    Study study = new Study();
    study.setBudget(request.getBudget());
    study.setExtractionAmount(request.getExtractionAmount());
    study.setReferringDoctor(request.getReferringDoctor());
    study.setType(request.getStudyType());
    study.setPresumptiveDiagnosis(request.getPresumptiveDiagnosis());
    study.setPatient(patientService.getPatient(patientId));
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User queriedUser = userService.getUser(currentUser.getId());
    Employee employee = employeeService.getByUser(queriedUser);
    Checkpoint checkpoint = new Checkpoint();
    checkpoint.setStudy(study);
    checkpoint.setCreatedBy(employee);
    checkpoint.setStatus(studyStatusService.getStudyStatus(1L));
    study.getCheckpoints().add(checkpoint);
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
      study.setType(request.getStudyType());
    } else {
      throw new BadRequestException("Ya se envio el consentimiento informado al paciente.");
    }
    study.setReferringDoctor(request.getReferringDoctor());
    study.setPresumptiveDiagnosis(request.getPresumptiveDiagnosis());
  }

}
