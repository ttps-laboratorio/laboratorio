package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.request.PatientDTO;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IPatientRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

	private final ContactService contactService;

	private final HealthInsuranceService healthInsuranceService;

	private final IPatientRepository patientRepository;

	public PatientService(IPatientRepository patientRepository,
												HealthInsuranceService healthInsuranceService,
												ContactService contactService) {
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
	public Patient createPatient(PatientDTO request) {
		if (patientRepository.existsByDni(request.getDni())) {
			throw new BadRequestException("Existe otro paciente con dni " + request.getDni());
		}
		Patient patient = new Patient();
		setPatient(patient, request);
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

	private void setPatient(Patient patient, PatientDTO request) {
		patient.setDni(request.getDni());
		patient.setFirstName(request.getFirstName());
		patient.setLastName(request.getLastName());
		patient.setBirthDate(request.getBirthDate());
		patient.setClinicHistory(request.getClinicHistory());
		patient.setAffiliateNumber(request.getAffiliateNumber());
		patient.setContact(contactService.createContact(request.getContact()));
		patient.setHealthInsurance(healthInsuranceService.getHealthInsurance(request.getHealthInsuranceId().longValue()));
		patientRepository.save(patient);
	}

}
