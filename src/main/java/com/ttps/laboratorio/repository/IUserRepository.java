package com.ttps.laboratorio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.laboratorio.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
}
