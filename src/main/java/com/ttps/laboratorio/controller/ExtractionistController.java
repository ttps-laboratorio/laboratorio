package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.entity.Extractionist;
import com.ttps.laboratorio.service.ExtractionistService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "extractionist")
public class ExtractionistController {

	private final ExtractionistService extractionistService;

	public ExtractionistController(ExtractionistService extractionistService) {
		this.extractionistService = extractionistService;
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/{id}")
	public ResponseEntity<Extractionist> getExtractionist(@PathVariable(name = "id") @NonNull Long extractionistId) {
		Extractionist extractionist = extractionistService.getExtractionist(extractionistId);
		return ResponseEntity.ok(extractionist);
	}

	/**
	 * View a list of all extractionists.
	 *
	 * @return Returns a list of all extractionists with "200 OK".
	 */
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping()
	public ResponseEntity<?> listExtractionists() {
		return ResponseEntity.ok(extractionistService.getAllExtractionists());
	}
}
