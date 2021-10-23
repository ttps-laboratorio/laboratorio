package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.PatientDTO;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.repository.IPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

  @Autowired
  ContactService contactService;

  private final IPatientRepository patientRepository;

  public PatientService (IPatientRepository patientRepository) {
    this.patientRepository = patientRepository;
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
    patient.setFirstName(request.getFirstName());
    patient.setLastName(request.getLastName());
    patient.setBirthDate(request.getBirthDate());
    patient.setClinicHistory(request.getClinicHistory());
    patient.setAffiliateNumber(request.getAffiliateNumber());
    patient.setContact(contactService.createContact(request.getContact()));
    //patient.setHealthInsurance(request.getHealthInsurance());
    patientRepository.save(patient);
  }
}
