package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.PresumptiveDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPresumptiveDiagnosisRepository extends JpaRepository<PresumptiveDiagnosis, Long> {

}
