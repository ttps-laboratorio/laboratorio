package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudyRepository extends JpaRepository<Study, Long> {

}
