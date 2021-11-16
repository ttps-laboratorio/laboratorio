package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.PatientDTO;
import com.ttps.laboratorio.entity.Contact;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IPatientRepository;
import java.util.ArrayList;
import java.util.List;

public class PatientService {

  private final IPatientRepository patientRepository;

  public PatientService (IPatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  public Patient getPatient(Long id) {
    return this.patientRepository.findById(id).orElseThrow();
  }

  /**
   * Gets all patients registered.
   * @return List of all the patients
   */
  public List<Patient> getAllPatients() {
    return new ArrayList<>(patientRepository.findAll());
  }

  /**
   * Creates new patient.
   * @param request patient information
   */
  public void createPatient(PatientDTO request) {
    Patient patient = new Patient();
    setPatient(patient, request);
  }

  /**
   * Updates an existing patient.
   * @param patientID id from the patient to search
   * @param request new data to change
   */
  public void updatePatient(Long patientID, PatientDTO request) {
    Patient patient = patientRepository.findById(patientID)
        .orElseThrow(() -> new NotFoundException("No existe un paciente con el id " + patientID + "."));
    setPatient(patient, request);
  }

  private void setPatient(Patient patient, PatientDTO request) {
    Contact contact = new Contact();
    contact.setName(request.getContactName());
    contact.setEmail(request.getContactEmail());
    contact.setPhoneNumber(request.getContactPhoneNumber());
    patient.setDni(request.getDni());
    patient.setFirstName(request.getFirstName());
    patient.setLastName(request.getLastName());
    patient.setBirthDate(request.getBirthDate());
    patient.setClinicHistory(request.getClinicHistory());
    patient.setContact(contact);
    patient.setAffiliateNumber(request.getAffiliateNumber());
    patient.setHealthInsurance(request.getHealthInsurance());
    patientRepository.save(patient);
  }

}
