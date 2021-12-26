package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGuardianRepository extends JpaRepository<Guardian, Long> {

}
