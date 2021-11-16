package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.PatientDTO;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.service.PatientService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "patient")
public class PatientController {

  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @PreAuthorize("hasRole('EMPLOYEE')")
  @GetMapping("/{id}")
  public ResponseEntity<Patient> getPatient(@PathVariable(name = "id") @NonNull Long patientId) {
    Patient patient = patientService.getPatient(patientId);
    return ResponseEntity.ok(patient);
  }

  /**
   * View a list of all patients.
   * @return  Returns a list of all patients with "200 OK".
   */
  @PreAuthorize("hasRole('EMPLOYEE')")
  @GetMapping()
  public ResponseEntity<?> listPatients() {
    return ResponseEntity.ok(patientService.getAllPatients());
  }

  /**
   * Registers a new Patient on the database.
   * @param patientDTO patient information
   * @return status
   */
  @PreAuthorize("hasRole('EMPLOYEE')")
  @PostMapping()
  public ResponseEntity<?> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
    patientService.createPatient(patientDTO);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Modifies a new Patient on the database.
   * @param patientDTO patient information
   * @return status
   */
  @PreAuthorize("hasRole('EMPLOYEE')")
  @PutMapping("/{id}")
  public ResponseEntity<?> updatePatient(@PathVariable(name = "id") @NonNull Long patientID,
                                        @Valid @RequestBody @NonNull PatientDTO patientDTO) {
    patientService.updatePatient(patientID, patientDTO);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
