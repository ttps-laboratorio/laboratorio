package com.ttps.laboratorio.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
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

import com.ttps.laboratorio.dto.request.AppointmentDTO;
import com.ttps.laboratorio.dto.request.SampleDTO;
import com.ttps.laboratorio.dto.request.StudyDTO;
import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.entity.Sample;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.service.AppointmentService;
import com.ttps.laboratorio.service.SampleService;
import com.ttps.laboratorio.service.StudyService;

@RestController
@RequestMapping(path = "study")
public class StudyController {

	private final StudyService studyService;

	private final AppointmentService appointmentService;

	private final SampleService sampleService;

	public StudyController(StudyService studyService, AppointmentService appointmentService, SampleService sampleService) {
		this.studyService = studyService;
		this.appointmentService = appointmentService;
		this.sampleService = sampleService;
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/{id}")
	public ResponseEntity<Study> getStudy(@PathVariable(name = "id") @NonNull Long studyId) {
		Study study = studyService.getStudy(studyId);
		return ResponseEntity.ok(study);
	}

	/**
	 * View a list of all studys.
	 *
	 * @return Returns a list of all studys with "200 OK".
	 */
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping()
	public ResponseEntity<?> listStudies() {
		return ResponseEntity.ok(studyService.getAllStudies());
	}

	/**
	 * Modifies an Study on the database.
	 *
	 * @param studyDTO study information
	 * @return status
	 */
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateStudy(@PathVariable(name = "id") @NonNull Long studyID,
																			 @Valid @RequestBody @NonNull StudyDTO studyDTO) {
		studyService.updateStudy(studyID, studyDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/pdf/budget/{id}")
	public void generateBudgetPDF(@PathVariable(name = "id") @NonNull Long studyID, HttpServletResponse response) throws IOException {
		studyService.downloadBudgetFile(studyID, response);
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping(path = "/{studyId}/appointment")
	public ResponseEntity<Appointment> createAppointment(@PathVariable(name = "studyId") @NonNull Long studyId,
																											 @Valid @RequestBody AppointmentDTO appointmentDTO) {
		return new ResponseEntity<>(appointmentService.createAppointment(studyId, appointmentDTO), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping(path = "/{studyId}/sample")
	public ResponseEntity<Sample> createSample(@PathVariable(name = "studyId") @NonNull Long studyId,
																									@Valid @RequestBody SampleDTO sampleDTO) {
		return new ResponseEntity<>(sampleService.createSample(studyId, sampleDTO), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping(path = "/{studyId}/extractionist/{extractionistId}")
	public ResponseEntity<Study> selectExtractionist(@PathVariable(name = "studyId") @NonNull Long studyId,
																										@PathVariable(name = "extractionistId") @NonNull Long extractionistId) {
		return new ResponseEntity<>(studyService.setExtractionistById(studyId, extractionistId), HttpStatus.CREATED);
	}

}
