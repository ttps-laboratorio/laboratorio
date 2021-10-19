package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.SampleBatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISampleBatchStatusRepository extends JpaRepository<SampleBatchStatus, Long> {

}
