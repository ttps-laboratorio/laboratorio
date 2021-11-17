package com.ttps.laboratorio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.laboratorio.entity.Patient;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Long> {

	boolean existsByDniAndIdNot(Long dni, Long patientID);

	boolean existsByDni(Long dni);

}
