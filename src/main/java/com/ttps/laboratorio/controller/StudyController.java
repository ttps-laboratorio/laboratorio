package com.ttps.laboratorio.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ttps.laboratorio.dto.request.AppointmentDTO;
import com.ttps.laboratorio.dto.request.ConfirmPaymentDTO;
import com.ttps.laboratorio.dto.request.FinalReportDTO;
import com.ttps.laboratorio.dto.request.SampleDTO;
import com.ttps.laboratorio.dto.request.StudyDTO;
import com.ttps.laboratorio.dto.request.StudySearchFilterDTO;
import com.ttps.laboratorio.dto.request.UnpaidStudiesDTO;
import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.entity.Sample;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.service.AppointmentService;
import com.ttps.laboratorio.service.ExtractionistService;
import com.ttps.laboratorio.service.FinalReportService;
import com.ttps.laboratorio.service.SampleService;
import com.ttps.laboratorio.service.StudyService;

@RestController
@RequestMapping(path = "study")
public class StudyController {

	private final StudyService studyService;

	private final AppointmentService appointmentService;

	private final SampleService sampleService;

	private final ExtractionistService extractionistService;

	private final FinalReportService finalReportService;

	public StudyController(StudyService studyService, AppointmentService appointmentService, SampleService sampleService,
												 ExtractionistService extractionistService, FinalReportService finalReportService) {
		this.studyService = studyService;
		this.appointmentService = appointmentService;
		this.sampleService = sampleService;
		this.extractionistService = extractionistService;
		this.finalReportService = finalReportService;
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/{id}")
	public ResponseEntity<Study> getStudy(@PathVariable(name = "id") @NonNull Long studyId) {
		return ResponseEntity.ok(studyService.getStudy(studyId));
	}

	/**
	 * View a list of all studys.
	 *
	 * @return Returns a list of all studys with "200 OK".
	 */
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping()
	public ResponseEntity<?> listStudies(@Valid StudySearchFilterDTO filter) {
		return ResponseEntity.ok(studyService.getAllStudies(filter));
	}

	/**
	 * Modifies an Study on the database.
	 *
	 * @param studyDTO study information
	 * @return status
	 */
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateStudy(@PathVariable(name = "id") @NonNull Long studyId,
																			 @Valid @RequestBody @NonNull StudyDTO studyDTO) {
		return ResponseEntity.ok(studyService.updateStudy(studyId, studyDTO));
	}

	@PreAuthorize("hasRole('EMPLOYEE') OR hasRole('PATIENT')")
	@GetMapping("/{id}/budget")
	public ResponseEntity<Resource> downloadBudgetPDF(@PathVariable(name = "id") @NonNull Long studyId)
			throws IOException {
		Resource file = studyService.downloadBudgetFile(studyId);
		Path path = file.getFile().toPath();
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PreAuthorize("hasRole('EMPLOYEE') OR hasRole('PATIENT')")
	@PostMapping(value = "/{studyId}/payment-proof")
	public ResponseEntity<Resource> uploadPaymentProofPDF(@PathVariable(name = "studyId") @NonNull Long studyId,
			@RequestPart MultipartFile paymentProofPdf) {
		Study study = studyService.uploadPaymentProofFile(studyId, paymentProofPdf);
		return ResponseEntity.ok(null);
	}

	@PreAuthorize("hasRole('EMPLOYEE') OR hasRole('PATIENT')")
	@GetMapping("/{studyId}/payment-proof")
	public ResponseEntity<Resource> downloadPaymentProofPDF(@PathVariable(name = "studyId") @NonNull Long studyId)
			throws IOException {
		Resource file = studyService.downloadPaymentProofFile(studyId);
		Path path = file.getFile().toPath();
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping("/{studyId}/confirm-payment")
	public ResponseEntity<Resource> confirmPayment(@PathVariable(name = "studyId") @NonNull Long studyId,
																								 @RequestBody ConfirmPaymentDTO confirmPayment) {
		Study study = studyService.confirmPayment(studyId, confirmPayment.isConfirm());
		return ResponseEntity.ok(null);
	}

	@PreAuthorize("hasRole('EMPLOYEE') OR hasRole('PATIENT')")
	@GetMapping("/{studyId}/consent")
	public ResponseEntity<Resource> downloadConsentPDF(@PathVariable(name = "studyId") @NonNull Long studyId)
			throws IOException {
		Resource file = studyService.downloadConsentFile(studyId);
		Path path = file.getFile().toPath();
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PreAuthorize("hasRole('EMPLOYEE') OR hasRole('PATIENT')")
	@PostMapping("/{studyId}/signed-consent")
	public ResponseEntity<Resource> uploadSignedConsentPDF(@PathVariable(name = "studyId") @NonNull Long studyId,
			@RequestPart MultipartFile signedConsentPdf) {
		Study study = studyService.uploadSignedConsentFile(studyId, signedConsentPdf);
		return ResponseEntity.ok(null);
	}

	@PreAuthorize("hasRole('EMPLOYEE') OR hasRole('PATIENT')")
	@GetMapping("/{studyId}/signed-consent")
	public ResponseEntity<Resource> downloadSignedConsentPDF(@PathVariable(name = "studyId") @NonNull Long studyId)
			throws IOException {
		Resource file = studyService.downloadSignedConsentFile(studyId);
		Path path = file.getFile().toPath();
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PreAuthorize("hasRole('EMPLOYEE') OR hasRole('PATIENT')")
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
	public ResponseEntity<?> selectExtractionist(@PathVariable(name = "studyId") @NonNull Long studyId,
																									 @PathVariable(name = "extractionistId") @NonNull Long extractionistId) {
		extractionistService.setExtractionistById(studyId, extractionistId);
		return ResponseEntity.ok(null);
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping(path = "/{id}/final-report")
	public ResponseEntity<?> createFinalReport(@PathVariable(name = "id") @NonNull Long studyId,
																						 @Valid @RequestBody FinalReportDTO finalReportDTO) {
		return new ResponseEntity<>(finalReportService.createFinalReport(studyId, finalReportDTO), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('EMPLOYEE') OR hasRole('PATIENT')")
	@GetMapping("/{id}/final-report")
	public ResponseEntity<Resource> downloadFinalReportPDF(@PathVariable(name = "id") @NonNull Long studyId)
			throws IOException {
		Resource file = studyService.downloadFinalReportFile(studyId);
		Path path = file.getFile().toPath();
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/unpaid-extraction")
	public ResponseEntity<?> listUnpaidExtractionStudies() {
		return ResponseEntity.ok(studyService.getAllUnpaidStudies());
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping(path = "/unpaid-extraction")
	public ResponseEntity<?> paySelectedExtractionStudies(@Valid @RequestBody UnpaidStudiesDTO unpaidStudiesDTO) {
		return ResponseEntity.ok(studyService.payExtractionAmountStudies(unpaidStudiesDTO));
	}

}
