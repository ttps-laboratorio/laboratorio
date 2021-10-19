package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.StudyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudyTypeRepository extends JpaRepository<StudyType, Long> {

}
