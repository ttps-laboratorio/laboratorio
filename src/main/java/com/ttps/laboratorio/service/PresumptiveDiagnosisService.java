package com.ttps.laboratorio.service;

import com.ttps.laboratorio.entity.PresumptiveDiagnosis;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IPresumptiveDiagnosisRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PresumptiveDiagnosisService {

	private final IPresumptiveDiagnosisRepository presumptiveDiagnosisRepository;

	public PresumptiveDiagnosisService(IPresumptiveDiagnosisRepository presumptiveDiagnosisRepository) {
		this.presumptiveDiagnosisRepository = presumptiveDiagnosisRepository;
	}

	/**
	 * Gets all presumptive diagnosis registered.
	 *
	 * @return List of all the presumptive diagnosis
	 */
	public List<PresumptiveDiagnosis> getAllPresumptiveDiagnosis() {
		return new ArrayList<>(presumptiveDiagnosisRepository.findAll());
	}

	public PresumptiveDiagnosis getPresumptiveDiagnosis(Long id) {
		return this.presumptiveDiagnosisRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No existe un diagnostico presuntivo con el id " + id + "."));
	}

}
