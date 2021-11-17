package com.ttps.laboratorio.service;

import com.ttps.laboratorio.entity.StudyType;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IStudyTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class StudyTypeService {

  private final IStudyTypeRepository studyTypeRepository;

  public StudyTypeService (IStudyTypeRepository studyTypeRepository) {
    this.studyTypeRepository = studyTypeRepository;
  }

  public StudyType getStudyType(Long id) {
    return this.studyTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un tipo de estudio con el id " + id + "."));
  }

}
