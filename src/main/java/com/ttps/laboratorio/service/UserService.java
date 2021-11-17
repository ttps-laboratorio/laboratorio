package com.ttps.laboratorio.service;

import com.ttps.laboratorio.repository.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final IUserRepository userRepository;

  public UserService (IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

}
