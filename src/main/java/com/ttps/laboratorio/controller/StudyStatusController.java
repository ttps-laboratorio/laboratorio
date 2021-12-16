package com.ttps.laboratorio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttps.laboratorio.dto.response.StudyStatusResponseDTO;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.service.StudyStatusService;

@RestController
@RequestMapping(path = "study-status")
public class StudyStatusController {

	private final StudyStatusService studyStatusService;

	public StudyStatusController(StudyStatusService studyStatusService) {
		this.studyStatusService = studyStatusService;
	}

	@PreAuthorize("hasRole('EMPLOYEE') OR hasRole('PATIENT')")
	@GetMapping("/{id}")
	public ResponseEntity<StudyStatus> getStudyType(@PathVariable(name = "id") @NonNull Long id) {
		return ResponseEntity.ok(studyStatusService.getStudyStatus(id));
	}

	/**
	 * View a list of all studyTypes.
	 *
	 * @return Returns a list of all studyTypes with "200 OK".
	 */
	@PreAuthorize("hasRole('EMPLOYEE') OR hasRole('PATIENT')")
	@GetMapping()
	public ResponseEntity<List<StudyStatusResponseDTO>> listStudyTypes() {
		return ResponseEntity.ok(studyStatusService.getAll());
	}
}
