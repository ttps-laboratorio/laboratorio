package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.entity.Sample;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import com.ttps.laboratorio.entity.StudyType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudyRepository extends JpaRepository<Study, Long>, JpaSpecificationExecutor<Study> {

	@Query("SELECT s FROM Study s JOIN s.checkpoints ch WHERE ch.createdAt IN (SELECT MAX(c.createdAt) FROM Checkpoint c WHERE c.study=s AND c.status= :status)")
	List<Study> findAllByActualStatus(@Param("status") StudyStatus status);

	Study findByAppointment(Appointment appointment);

	Optional<Study> findBySample(Sample sample);

	List<Study> findByPatient(Patient patient);

	List<Study> findByPaidExtractionAmountFalse();

	List<Study> findByType(StudyType type);

	List<Study> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

}
