package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.entity.StudyType;
import com.ttps.laboratorio.service.StudyTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "study-type")
public class StudyTypeController {

	private final StudyTypeService studyTypeService;

	public StudyTypeController(StudyTypeService studyTypeService) {
		this.studyTypeService = studyTypeService;
	}

	@PreAuthorize("hasRole('EMPLOYEE') OR hasRole('PATIENT')")
	@GetMapping("/{id}")
	public ResponseEntity<StudyType> getStudyType(@PathVariable(name = "id") @NonNull Long studyTypeId) {
		return ResponseEntity.ok(studyTypeService.getStudyType(studyTypeId));
	}

	/**
	 * View a list of all studyTypes.
	 *
	 * @return Returns a list of all studyTypes with "200 OK".
	 */
	@PreAuthorize("hasRole('EMPLOYEE') OR hasRole('PATIENT')")
	@GetMapping()
	public ResponseEntity<?> listStudyTypes() {
		return ResponseEntity.ok(studyTypeService.getAllStudyTypes());
	}
}
