package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.Appointment;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.entity.StudyStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudyRepository extends JpaRepository<Study, Long> {

	@Query("SELECT s FROM Study s JOIN s.checkpoints ch WHERE ch.createdAt IN (SELECT MAX(c.createdAt) FROM Checkpoint c WHERE c.study=s AND c.status= :status)")
	List<Study> findAllByActualStatus(@Param("status") StudyStatus status);

	Study findByAppointment(Appointment appointment);

}
