package com.ttps.laboratorio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ttps.laboratorio.dto.response.StudyStatusResponseDTO;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IStudyStatusRepository;

@Service
public class StudyStatusService {

	private final IStudyStatusRepository studyStatusRepository;

	private final ModelMapper mapper;

	public StudyStatusService(IStudyStatusRepository studyStatusRepository) {
		this.studyStatusRepository = studyStatusRepository;
		this.mapper = new ModelMapper();
	}

	public StudyStatus getStudyStatus(Long id) {
		return this.studyStatusRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un estado con el id " + id + "."));
	}

	public List<StudyStatusResponseDTO> getAll() {
		return this.studyStatusRepository.findAll(Sort.by("order")).stream()
				.map(ss -> this.mapper.map(ss, StudyStatusResponseDTO.class)).collect(Collectors.toList());
	}
}
