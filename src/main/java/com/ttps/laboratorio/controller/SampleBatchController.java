package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.response.SampleBatchDTO;
import com.ttps.laboratorio.service.SampleBatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "sample-batch")
public class SampleBatchController {

	private final SampleBatchService sampleBatchService;

	public SampleBatchController(SampleBatchService sampleBatchService) {
		this.sampleBatchService = sampleBatchService;
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/{id}")
	public ResponseEntity<SampleBatchDTO> getSampleBatch(@PathVariable(name = "id") @NonNull Long sampleBatchId) {
		SampleBatchDTO sampleBatch = sampleBatchService.getSampleBatch(sampleBatchId);
		return ResponseEntity.ok(sampleBatch);
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping()
	public ResponseEntity<?> listSampleBatches() {
		return ResponseEntity.ok(sampleBatchService.getAllSampleBatches());
	}

}
