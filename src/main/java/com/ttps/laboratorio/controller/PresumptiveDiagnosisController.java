package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.service.PresumptiveDiagnosisService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "presumptive-diagnosis")
public class PresumptiveDiagnosisController {

	private final PresumptiveDiagnosisService presumptiveDiagnosisService;

	public PresumptiveDiagnosisController(PresumptiveDiagnosisService presumptiveDiagnosisService) {
		this.presumptiveDiagnosisService = presumptiveDiagnosisService;
	}

	/**
	 * View a list of all presumptive diagnosis.
	 * 
	 * @return Returns a list of all presumptive diagnosis with "200 OK".
	 */
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping(path = "/")
	public ResponseEntity<?> listPresumptiveDiagnosis() {
		return ResponseEntity.ok(presumptiveDiagnosisService.getAllPresumptiveDiagnosis());
	}

}
