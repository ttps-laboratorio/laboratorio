package com.ttps.laboratorio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.laboratorio.entity.StudyStatus;

@Repository
public interface IStudyStatusRepository extends JpaRepository<StudyStatus, Long> {

	Optional<StudyStatus> findByOrder(Integer order);
}
