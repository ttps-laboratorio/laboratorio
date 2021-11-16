package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.BlockedDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlockedDayRepository extends JpaRepository<BlockedDay, Long> {

}
