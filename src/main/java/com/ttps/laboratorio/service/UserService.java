package com.ttps.laboratorio.service;

import com.ttps.laboratorio.entity.User;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final IUserRepository userRepository;

	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUser(Long id) {
		return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un usuario con el id " + id + "."));
	}

}
