package com.ttps.laboratorio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttps.laboratorio.dto.request.PatientDTO;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.LaboratoryException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IPatientRepository;
import com.ttps.laboratorio.utils.LaboratoryFileUtils;

@Service
public class PatientService {

	private final ContactService contactService;

	private final HealthInsuranceService healthInsuranceService;

	private final IPatientRepository patientRepository;

	public PatientService(IPatientRepository patientRepository,
												HealthInsuranceService healthInsuranceService,
			ContactService contactService, LaboratoryFileUtils laboratoryFileUtils) {
		this.patientRepository = patientRepository;
		this.healthInsuranceService = healthInsuranceService;
		this.contactService = contactService;
	}

	public Patient getPatient(Long id) {
		return this.patientRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un paciente con el id " + id + "."));
	}

	/**
	 * Gets all patients registered.
	 *
	 * @return List of all the patients
	 */
	public List<Patient> getAllPatients() {
		return new ArrayList<>(patientRepository.findAll());
	}

	/**
	 * Creates new patient.
	 *
	 * @param request patient information
	 */
	@Transactional(rollbackFor = { LaboratoryException.class, Exception.class })
	public Patient createPatient(PatientDTO request) {
		if (patientRepository.existsByDni(request.getDni())) {
			throw new BadRequestException("Existe otro paciente con dni " + request.getDni());
		}
		Patient patient = new Patient();
		patient = setPatient(patient, request);
		return patient;
	}

	/**
	 * Updates an existing patient.
	 *
	 * @param patientID id from the patient to search
	 * @param request   new data to change
	 */
	public void updatePatient(Long patientID, PatientDTO request) {
		Patient patient = patientRepository.findById(patientID)
				.orElseThrow(() -> new NotFoundException("No existe un paciente con el id " + patientID + "."));
		if (patientRepository.existsByDniAndIdNot(request.getDni(), patientID)) {
			throw new BadRequestException("Existe otro paciente con dni " + request.getDni());
		}
		setPatient(patient, request);
	}

	private Patient setPatient(Patient patient, PatientDTO dto) {
		patient.setDni(dto.getDni());
		patient.setFirstName(dto.getFirstName());
		patient.setLastName(dto.getLastName());
		patient.setBirthDate(dto.getBirthDate());
		patient.setClinicHistory(dto.getClinicHistory());
		patient.setAffiliateNumber(dto.getAffiliateNumber());
		patient.setContact(contactService.createContact(dto.getContact()));
		patient.setHealthInsurance(healthInsuranceService.getHealthInsurance(dto.getHealthInsurance().getId()));
		return patientRepository.save(patient);
	}

}
