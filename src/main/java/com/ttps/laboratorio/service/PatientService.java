package com.ttps.laboratorio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttps.laboratorio.dto.PatientDTO;
import com.ttps.laboratorio.dto.StudyDTO;
import com.ttps.laboratorio.entity.Contact;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IDoctorRepository;
import com.ttps.laboratorio.repository.IExtractionistRepository;
import com.ttps.laboratorio.repository.IHealthInsuranceRepository;
import com.ttps.laboratorio.repository.IPatientRepository;
import com.ttps.laboratorio.repository.IPresumptiveDiagnosisRepository;
import com.ttps.laboratorio.repository.IStudyRepository;
import com.ttps.laboratorio.repository.IStudyTypeRepository;

@Service
public class PatientService {

	@Autowired
	IPresumptiveDiagnosisRepository presumptiveDiagnosisRepository;
	@Autowired
	IExtractionistRepository extractionistRepository;
	@Autowired
	IDoctorRepository doctorRepository;
	@Autowired
	ContactService contactService;
	@Autowired
	IStudyTypeRepository studyTypeRepository;

	@Autowired
	private IStudyRepository studyRepository;

	@Autowired
	private IHealthInsuranceRepository healthInsuranceRepository;
	private final IPatientRepository patientRepository;

	public PatientService(IPatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	public Patient getPatient(Long id) {
		return this.patientRepository.findById(id).orElseThrow();
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
	public void createPatient(PatientDTO request) {
		if (patientRepository.existsByDni(request.getDni()))
			throw new BadRequestException("Existe otro paciente con dni " + request.getDni());
		Patient patient = new Patient();
		setPatient(patient, request);
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
		if (patientRepository.existsByDniAndIdNot(request.getDni(), patientID))
			throw new BadRequestException("Existe otro paciente con dni " + request.getDni());
		setPatient(patient, request);
	}

	private void setPatient(Patient patient, PatientDTO request) {
		Contact contact = new Contact();
		contact.setName(request.getContact().getName());
		contact.setEmail(request.getContact().getEmail());
		contact.setPhoneNumber(request.getContact().getPhoneNumber());
		patient.setDni(request.getDni());
		patient.setFirstName(request.getFirstName());
		patient.setLastName(request.getLastName());
		patient.setBirthDate(request.getBirthDate());
		patient.setClinicHistory(request.getClinicHistory());
		patient.setAffiliateNumber(request.getAffiliateNumber());
		patient.setContact(contactService.createContact(request.getContact()));
		patient.setHealthInsurance(healthInsuranceRepository.findById(request.getHealthInsurance().getId())
				.orElseThrow(() -> new NotFoundException("A health insurance with the id "
						+ request.getHealthInsurance().getId() + " does not exist.")));
		patientRepository.save(patient);
	}

	/**
	 * Creates a study
	 * 
	 * @param patientID id from the patient to search
	 * @param request   study data
	 */
	public void createStudy(Long patientID, StudyDTO request) {
		Patient patient = patientRepository.findById(patientID)
				.orElseThrow(() -> new NotFoundException("A patient with the id " + patientID + " does not exist."));
		Study study = new Study();
		study.setCreated_at(request.getCreated_at());
		study.setBudget(request.getBudget());
		study.setExtractionAmount(request.getExtractionAmount());
		study.setPaidExtractionAmount(request.getPaidExtractionAmount());
		// study.setPositiveResult(request.getPositiveResult());
		study.setDelay(request.getDelay());
		study.setPatient(patient);
		study.setType(studyTypeRepository.findById(request.getType().getId()).orElseThrow(() -> new NotFoundException(
				"A study type with the id " + request.getType().getId() + " does not exist.")));
		study.setReferringDoctor(
				doctorRepository.findById(request.getReferringDoctor().getId()).orElseThrow(() -> new NotFoundException(
						"A doctor with the id " + request.getReferringDoctor().getId() + " does not exist.")));
		study.setExtractionist(extractionistRepository.findById(request.getExtractionist().getId())
				.orElseThrow(() -> new NotFoundException(
						"An extractionist with the id " + request.getExtractionist().getId() + " does not exist.")));
		study.setPresumptiveDiagnosis(presumptiveDiagnosisRepository.findById(request.getPresumptiveDiagnosis().getId())
				.orElseThrow(() -> new NotFoundException("A presumptive diagnosis with the id "
						+ request.getPresumptiveDiagnosis().getId() + " does not exist.")));
		// Tema de samples y sample batch ?????????
		// studyRepository.save(study);
		patient.addStudy(study); // Necesario?
		patientRepository.save(patient);

	}
}
