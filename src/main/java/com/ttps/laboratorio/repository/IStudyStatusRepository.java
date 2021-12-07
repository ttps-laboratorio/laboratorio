package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.StudyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudyStatusRepository extends JpaRepository<StudyStatus, Long> {

}
