package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICheckpointRepository extends JpaRepository<Checkpoint, Long> {

}
