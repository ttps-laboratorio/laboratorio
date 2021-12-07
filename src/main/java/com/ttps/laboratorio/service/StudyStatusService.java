package com.ttps.laboratorio.service;

import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IStudyStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class StudyStatusService {

	private final IStudyStatusRepository studyStatusRepository;

	public StudyStatusService(IStudyStatusRepository studyStatusRepository) {
		this.studyStatusRepository = studyStatusRepository;
	}

	public StudyStatus getStudyStatus(Long id) {
		return this.studyStatusRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un estado con el id " + id + "."));
	}

}
