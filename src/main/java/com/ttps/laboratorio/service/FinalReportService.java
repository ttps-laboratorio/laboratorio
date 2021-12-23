package com.ttps.laboratorio.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttps.laboratorio.dto.request.FinalReportDTO;
import com.ttps.laboratorio.entity.Employee;
import com.ttps.laboratorio.entity.FinalReport;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.entity.User;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.LaboratoryException;
import com.ttps.laboratorio.repository.IFinalReportRepository;
import com.ttps.laboratorio.utils.LaboratoryFileUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FinalReportService {

	private final IFinalReportRepository finalReportRepository;

	private final StudyService studyService;

	private final UserService userService;

	private final EmployeeService employeeService;

	private final LaboratoryFileUtils laboratoryFileUtils;

	private final PdfGeneratorService pdfGeneratorService;

	public FinalReportService(IFinalReportRepository finalReportRepository, StudyService studyService, UserService userService, EmployeeService employeeService, LaboratoryFileUtils laboratoryFileUtils, PdfGeneratorService pdfGeneratorService) {
		this.finalReportRepository = finalReportRepository;
		this.studyService = studyService;
		this.userService = userService;
		this.employeeService = employeeService;
		this.laboratoryFileUtils = laboratoryFileUtils;
		this.pdfGeneratorService = pdfGeneratorService;
	}

	@Transactional(rollbackFor = { LaboratoryException.class, Exception.class })
	public FinalReport createFinalReport(Long studyId, FinalReportDTO finalReportDTO) {
		Study study = studyService.getStudy(studyId);
		if (study.getActualStatus() != null && !study.getActualStatus().getId().equals(StudyStatus.ESPERANDO_INTERPRETACION_DE_RESULTADOS)) {
			throw new BadRequestException(
					"El estudio #" + studyId + " no se encuentra en el estado correspondiente para ingresar el reporte final.");
		}
		User queriedUser = userService.getLoggedUser();
		Employee employee = employeeService.getByUser(queriedUser);
		FinalReport.builder().medicalInformant(employee).positiveResult(finalReportDTO.getPositiveResult())
				.report(finalReportDTO.getReport()).build();
		FinalReport finalReport = new FinalReport();
		finalReport.setPositiveResult(finalReportDTO.getPositiveResult());
		finalReport.setMedicalInformant(employee);
		finalReport.setReport(finalReportDTO.getReport());
		finalReport.setStudy(study);
		study.setFinalReport(finalReport);
		studyService.addCheckpointWithLoggedUser(StudyStatus.ESPERANDO_SER_ENTREGADO_A_MEDICO_DERIVANTE, study);
		studyService.saveStudy(study);
		try {
			String filename = laboratoryFileUtils.getFilenameFinalReport(study.getPatient().getId(), study.getId());
			pdfGeneratorService.generateFinalReport(study, filename);
		} catch (IOException e) {
			log.error("No se pudo generar el pdf del resultado final del paciente [dni: " + study.getPatient().getDni() + "]", e);
			throw new LaboratoryException("No se pudo generar el resultado final del paciente #" + study.getPatient().getId());
		}
		return finalReport;
	}

}
