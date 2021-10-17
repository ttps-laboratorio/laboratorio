package com.ttps.laboratorio.service;

import com.ttps.laboratorio.repository.IHealthInsuranceRepository;

public class HealthInsuranceService {

  private final IHealthInsuranceRepository healthInsuranceRepository;

  public HealthInsuranceService (IHealthInsuranceRepository healthInsuranceRepository) {
    this.healthInsuranceRepository = healthInsuranceRepository;
  }

}
