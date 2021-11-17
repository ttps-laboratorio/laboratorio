package com.ttps.laboratorio.controller;

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

import com.ttps.laboratorio.dto.DoctorDTO;
import com.ttps.laboratorio.entity.Doctor;
import com.ttps.laboratorio.service.DoctorService;

@RestController
@RequestMapping(path = "doctor")
public class DoctorController {

  private final DoctorService doctorService;

  public DoctorController(DoctorService doctorService) {
    this.doctorService = doctorService;
  }

	@PreAuthorize("hasRole('CONFIGURATOR')")
	@GetMapping("/{id}")
	public ResponseEntity<Doctor> getDoctor(@PathVariable(name = "id") @NonNull Long doctorId) {
		Doctor doctor = doctorService.getDoctor(doctorId);
		return ResponseEntity.ok(doctor);
	}

  /**
   * View a list of all doctors.
   * @return  Returns a list of all doctors with "200 OK".
   */
  @PreAuthorize("hasRole('CONFIGURATOR')")
	@GetMapping()
  public ResponseEntity<?> listDoctors() {
    return ResponseEntity.ok(doctorService.getAllDoctors());
  }

  /**
   * Registers a new Doctor on the database.
   * @param doctorDTO doctor information
   * @return status
   */
  @PreAuthorize("hasRole('CONFIGURATOR')")
	@PostMapping()
  public ResponseEntity<?> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
    doctorService.createDoctor(doctorDTO);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Modifies a new Doctor on the database.
   * @param doctorDTO doctor information
   * @return status
   */
  @PreAuthorize("hasRole('CONFIGURATOR')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateDoctor(@PathVariable(name = "id") @NonNull Long doctorID,
			@Valid @RequestBody @NonNull DoctorDTO doctorDTO) {
    doctorService.updateDoctor(doctorID, doctorDTO);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
