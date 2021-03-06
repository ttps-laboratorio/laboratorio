package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.request.HealthInsuranceDTO;
import com.ttps.laboratorio.entity.HealthInsurance;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IHealthInsuranceRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HealthInsuranceService {

	private final IHealthInsuranceRepository healthInsuranceRepository;

	public HealthInsuranceService(IHealthInsuranceRepository healthInsuranceRepository) {
		this.healthInsuranceRepository = healthInsuranceRepository;
	}

	public HealthInsurance getHealthInsurance(Long id) {
		return this.healthInsuranceRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No existe una obra social con el id " + id + "."));
	}

	/**
	 * Gets all health insurances registered.
	 *
	 * @return List of all the health insurances
	 */
	public List<HealthInsurance> getAllHealthInsurances() {
		return new ArrayList<>(healthInsuranceRepository.findAll());
	}

	/**
	 * Creates new health insurance.
	 *
	 * @param request health insurance information
	 */
	public HealthInsurance createHealthInsurance(HealthInsuranceDTO request) {
		HealthInsurance healthInsurance = new HealthInsurance();
		healthInsurance.setName(request.getName());
		healthInsurance.setPhoneNumber(request.getPhoneNumber());
		healthInsurance.setEmail(request.getEmail());
		healthInsuranceRepository.save(healthInsurance);
		return healthInsurance;
	}

	/**
	 * Updates an existing health insurance.
	 *
	 * @param healthInsuranceID id from the health insurance to search
	 * @param request           new data to change
	 */
	public HealthInsurance updateHealthInsurance(Long healthInsuranceID, HealthInsuranceDTO request) {
		HealthInsurance healthInsurance = healthInsuranceRepository.findById(healthInsuranceID).orElseThrow(
				() -> new NotFoundException("No existe una obra social con el id " + healthInsuranceID + "."));
		healthInsurance.setName(request.getName());
		healthInsurance.setPhoneNumber(request.getPhoneNumber());
		healthInsurance.setEmail(request.getEmail());
		healthInsuranceRepository.save(healthInsurance);
		return healthInsurance;
	}

	/**
	 * Deletes a health insurance.
	 *
	 * @param healthInsuranceID id from the health insurance to delete
	 */
	public void deleteHealthInsurance(Long healthInsuranceID) {
		healthInsuranceRepository.delete(healthInsuranceRepository.findById(healthInsuranceID).orElseThrow(
				() -> new NotFoundException("No existe una obra social con el id " + healthInsuranceID + ".")));
	}

}
