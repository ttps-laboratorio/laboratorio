package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.PatientDTO;
import com.ttps.laboratorio.dto.StudyDTO;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IPatientRepository;
import com.ttps.laboratorio.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "patient")
public class PatientController {

    private final PatientService patientService;
    @Autowired
    private IPatientRepository patientRepository;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * View a list of all patients.
     *
     * @return Returns a list of all patients with "200 OK".
     */
    @PreAuthorize("hasRole('CONFIGURATOR')")
    @GetMapping
    public ResponseEntity<?> listPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    /**
     * Registers a new Patient on the database.
     *
     * @param patientDTO patient information
     * @return status
     */
    @PreAuthorize("hasRole('CONFIGURATOR')")
    @PostMapping
    public ResponseEntity<?> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        patientService.createPatient(patientDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Modifies a new Patient on the database.
     *
     * @param patientDTO patient information
     * @return status
     */
    @PreAuthorize("hasRole('CONFIGURATOR')")
    @PutMapping
    public ResponseEntity<?> updatePatient(@RequestParam(name = "patientId") @NonNull Long patientID, @Valid @RequestBody @NonNull PatientDTO patientDTO) {
        patientService.updatePatient(patientID, patientDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Registers a new Study for a specific Patient on the database.
     *
     * @param request Study information
     * @return status
     */
    @PreAuthorize("hasRole('CONFIGURATOR')")
    @PostMapping(path = "/{patientID}/study")
    public ResponseEntity<?> createStudy(@PathVariable long patientID, @Valid @RequestBody StudyDTO request) {
        patientService.createStudy(patientID, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
