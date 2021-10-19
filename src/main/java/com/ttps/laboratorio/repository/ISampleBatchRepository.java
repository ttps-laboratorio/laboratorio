package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.SampleBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISampleBatchRepository extends JpaRepository<SampleBatch, Long> {

}
