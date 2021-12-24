package com.ttps.laboratorio.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.entity.Sample;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyType;

@Repository
public interface IStudyRepository extends JpaRepository<Study, Long>, JpaSpecificationExecutor<Study> {

	Study findByAppointment(Appointment appointment);

	Optional<Study> findBySample(Sample sample);

	List<Study> findByPatient(Patient patient);

	List<Study> findByPaidExtractionAmountFalse();

	List<Study> findByType(StudyType type);

	List<Study> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

}
