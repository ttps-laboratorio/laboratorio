package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.FinalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFinalReportRepository extends JpaRepository<FinalReport, Long> {
}
