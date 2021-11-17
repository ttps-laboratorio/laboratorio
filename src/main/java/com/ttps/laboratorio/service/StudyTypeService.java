package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.HealthInsuranceDTO;
import com.ttps.laboratorio.dto.StudyTypeDTO;
import com.ttps.laboratorio.entity.HealthInsurance;
import com.ttps.laboratorio.entity.StudyType;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IStudyTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudyTypeService {

	private final IStudyTypeRepository studyTypeRepository;

	public StudyTypeService(IStudyTypeRepository studyTypeRepository) {
		this.studyTypeRepository = studyTypeRepository;
	}

	/**
	 * Gets all study types registered.
	 * 
	 * @return List of all the study types
	 */
	public List<StudyType> getAllStudyTypes() {
		return new ArrayList<>(studyTypeRepository.findAll());
	}

	/**
	 * Creates new study type.
	 * 
	 * @param request study type information
	 */
	public StudyType createStudyType(StudyTypeDTO request) {
		StudyType studyType = new StudyType();
		studyType.setName(request.getName());
		studyType.setConsent(request.getConsent());
		studyTypeRepository.save(studyType);
		return studyType;
	}

	/**
	 * Updates an existing study type.
	 * 
	 * @param studyTypeID id from the study type to search
	 * @param request     new data to change
	 */
	public void updateStudyType(Long studyTypeID, StudyTypeDTO request) {
		StudyType studyType = studyTypeRepository.findById(studyTypeID).orElseThrow(
				() -> new NotFoundException("A study type with the id " + studyTypeID + " does not exist."));
		studyType.setName(request.getName());
		studyType.setConsent(request.getConsent());
		studyTypeRepository.save(studyType);
	}

	/**
	 * Deletes a study type.
	 * 
	 * @param studyTypeID id from the study type to delete
	 */
	public void deleteStudyType(Long studyTypeID) {
		studyTypeRepository.delete(studyTypeRepository.findById(studyTypeID).orElseThrow(
				() -> new RuntimeException("A study type with the id " + studyTypeID + " does not exist.")));
	}
}
