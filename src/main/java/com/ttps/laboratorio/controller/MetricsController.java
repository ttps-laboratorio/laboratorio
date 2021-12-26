package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.response.StudiesByMonthOfYearDTO;
import com.ttps.laboratorio.dto.response.StudiesByStatusDTO;
import com.ttps.laboratorio.dto.response.StudiesByStudyTypeDTO;
import com.ttps.laboratorio.service.MetricsService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "metrics")
public class MetricsController {

	private final MetricsService metricsService;

	public MetricsController(MetricsService metricsService) {
		this.metricsService = metricsService;
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/studies-by-type")
	public ResponseEntity<List<StudiesByStudyTypeDTO>> getStudiesByStudyType() {
		return ResponseEntity.ok(metricsService.listStudiesByStudyType());
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/{year}/studies-by-month")
	public ResponseEntity<StudiesByMonthOfYearDTO> getStudiesByMonthOfYear(@PathVariable(name = "year") @NonNull Integer year) {
		return ResponseEntity.ok(metricsService.listStudiesByMonthOfYear(year));
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/studies-by-status")
	public ResponseEntity<List<StudiesByStatusDTO>> getStudiesByStatus() {
		return ResponseEntity.ok(metricsService.listStudiesByStatus());
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/years-with-studies")
	public ResponseEntity<List<Integer>> getYearsWithStudies() {
		return ResponseEntity.ok(metricsService.listYearsWithStudies());
	}

}
