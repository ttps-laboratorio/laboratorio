package com.ttps.laboratorio.service;

import com.ttps.laboratorio.repository.IPatientRepository;

public class PatientService {

  private final IPatientRepository patientRepository;

  public PatientService (IPatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

}
