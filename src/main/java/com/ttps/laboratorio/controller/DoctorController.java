package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.request.DoctorDTO;
import com.ttps.laboratorio.entity.Doctor;
import com.ttps.laboratorio.service.DoctorService;
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
@RequestMapping(path = "doctor")
public class DoctorController {

	private final DoctorService doctorService;

	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
	@GetMapping("/{id}")
	public ResponseEntity<Doctor> getDoctor(@PathVariable(name = "id") @NonNull Long doctorId) {
		return ResponseEntity.ok(doctorService.getDoctor(doctorId));
	}

	/**
	 * View a list of all doctors.
	 *
	 * @return Returns a list of all doctors with "200 OK".
	 */
	@PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
	@GetMapping()
	public ResponseEntity<?> listDoctors() {
		return ResponseEntity.ok(doctorService.getAllDoctors());
	}

	/**
	 * Registers a new Doctor on the database.
	 *
	 * @param doctorDTO doctor information
	 * @return status
	 */
	@PreAuthorize("hasRole('CONFIGURATOR')")
	@PostMapping()
	public ResponseEntity<Doctor> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
		return new ResponseEntity<>(doctorService.createDoctor(doctorDTO), HttpStatus.CREATED);
	}

	/**
	 * Modifies a new Doctor on the database.
	 *
	 * @param doctorDTO doctor information
	 * @return status
	 */
	@PreAuthorize("hasRole('CONFIGURATOR')")
	@PutMapping("/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable(name = "id") @NonNull Long doctorID,
																				@Valid @RequestBody @NonNull DoctorDTO doctorDTO) {
		return new ResponseEntity<>(doctorService.updateDoctor(doctorID, doctorDTO), HttpStatus.OK);
	}

}
