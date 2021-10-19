package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISampleRepository extends JpaRepository<Sample, Long> {

}
