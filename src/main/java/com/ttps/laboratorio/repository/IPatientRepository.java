package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Long> {

	boolean existsByDniAndIdNot(Long dni, Long patientID);

	boolean existsByDni(Long dni);

}
