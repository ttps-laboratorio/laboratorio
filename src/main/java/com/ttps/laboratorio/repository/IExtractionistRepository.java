package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.Extractionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExtractionistRepository extends JpaRepository<Extractionist, Long> {

}
