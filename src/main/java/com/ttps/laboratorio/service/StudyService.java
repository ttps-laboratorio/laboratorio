package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.PatientDTO;
import com.ttps.laboratorio.dto.StudyDTO;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.repository.IPatientRepository;
import com.ttps.laboratorio.repository.IStudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudyService {

	@Autowired
	private IPatientRepository patientRepository;
	private final IStudyRepository studyRepository;

	public StudyService(IStudyRepository studyRepository) {
		this.studyRepository = studyRepository;
	}

	/**
	 * Gets all studies registered.
	 *
	 * @return List of all the studies
	 */
	public List<Study> getAllStudies() {
		return new ArrayList<>(studyRepository.findAll());
	}

}