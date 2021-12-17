package com.ttps.laboratorio.service;

import com.ttps.laboratorio.entity.Extractionist;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IExtractionistRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExtractionistService {

	private final IExtractionistRepository extractionistRepository;

	public ExtractionistService(IExtractionistRepository extractionistRepository) {
		this.extractionistRepository = extractionistRepository;
	}

	public Extractionist getExtractionist(Long extractionistId) {
		return this.extractionistRepository.findById(extractionistId)
				.orElseThrow(() -> new NotFoundException("No existe un extracionista con el id " + extractionistId + "."));
	}

	public List<Extractionist> getAllExtractionists() {
		return new ArrayList<>(extractionistRepository.findAll());
	}
}
