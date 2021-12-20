package com.ttps.laboratorio.service;

import com.ttps.laboratorio.entity.User;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IUserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

	public User getUserByUsername(String username) {
		return this.userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("No existe un usuario con el nombre de usuario " + username + "."));
	}

	public User getLoggedUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return getUserByUsername(username);
	}

}
