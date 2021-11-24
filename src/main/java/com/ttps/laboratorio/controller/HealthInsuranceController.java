package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.request.HealthInsuranceDTO;
import com.ttps.laboratorio.entity.HealthInsurance;
import com.ttps.laboratorio.service.HealthInsuranceService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/health-insurance")
public class HealthInsuranceController {

	private final HealthInsuranceService healthInsuranceService;

	public HealthInsuranceController(HealthInsuranceService healthInsuranceService) {
		this.healthInsuranceService = healthInsuranceService;
	}

	/**
	 * View a list of all health insurances.
	 *
	 * @return Returns a list of all health insurances with "200 OK".
	 */
	@PreAuthorize("hasRole('CONFIGURATOR') OR hasRole('EMPLOYEE')")
	@GetMapping
	public ResponseEntity<?> listHealthInsurance() {
		return ResponseEntity.ok(healthInsuranceService.getAllHealthInsurances());
	}

	@PreAuthorize("hasRole('CONFIGURATOR')")
	@GetMapping("/{id}")
	public ResponseEntity<HealthInsurance> getHealthInsurance(
			@PathVariable(name = "id") @NonNull Long healthInsuranceID) {
		HealthInsurance healthInsurance = healthInsuranceService.getHealthInsurance(healthInsuranceID);
		return ResponseEntity.ok(healthInsurance);
	}

	/**
	 * Registers a new Health Insurance on the database.
	 *
	 * @param healthInsuranceDTO health insurance information
	 * @return status
	 */
	@PreAuthorize("hasRole('CONFIGURATOR')")
	@PostMapping
	public ResponseEntity<?> createHealthInsurance(@Valid @RequestBody HealthInsuranceDTO healthInsuranceDTO) {
		healthInsuranceService.createHealthInsurance(healthInsuranceDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * Modifies a new Health Insurance on the database.
	 *
	 * @param healthInsuranceDTO health insurance information
	 * @return status
	 */
	@PreAuthorize("hasRole('CONFIGURATOR')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateHealthInsurance(@PathVariable(name = "id") @NonNull Long healthInsuranceID,
																								 @Valid @RequestBody @NonNull HealthInsuranceDTO healthInsuranceDTO) {
		healthInsuranceService.updateHealthInsurance(healthInsuranceID, healthInsuranceDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasRole('CONFIGURATOR')")
	@DeleteMapping
	public ResponseEntity<?> deleteHealthInsurance(
			@RequestParam(name = "healthInsuranceId") @NonNull Long healthInsuranceID) {
		healthInsuranceService.deleteHealthInsurance(healthInsuranceID);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
