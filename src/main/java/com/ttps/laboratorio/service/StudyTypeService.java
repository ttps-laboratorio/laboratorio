package com.ttps.laboratorio.service;

import com.ttps.laboratorio.entity.StudyType;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IStudyTypeRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudyTypeService {

	private final IStudyTypeRepository studyTypeRepository;

	public StudyTypeService(IStudyTypeRepository studyTypeRepository) {
		this.studyTypeRepository = studyTypeRepository;
	}

	public StudyType getStudyType(Long id) {
		return this.studyTypeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No existe un tipo de estudio con el id " + id + "."));
	}

	/**
	 * Gets all study types registered.
	 *
	 * @return List of all the study types
	 */
	public List<StudyType> getAllStudyTypes() {
		return new ArrayList<>(studyTypeRepository.findAll());
	}

}
