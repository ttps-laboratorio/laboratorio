package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.HealthInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHealthInsuranceRepository extends JpaRepository<HealthInsurance, Long> {

}
