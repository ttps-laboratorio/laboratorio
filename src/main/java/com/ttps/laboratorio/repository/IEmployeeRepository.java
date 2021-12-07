package com.ttps.laboratorio.repository;

import com.ttps.laboratorio.entity.Employee;
import com.ttps.laboratorio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByUser(User user);

}
