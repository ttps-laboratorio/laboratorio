package com.ttps.laboratorio.service;

import com.ttps.laboratorio.repository.IStudyRepository;

public class StudyService {

  private final IStudyRepository studyRepository;

  public StudyService (IStudyRepository studyRepository) {
    this.studyRepository = studyRepository;
  }

}
