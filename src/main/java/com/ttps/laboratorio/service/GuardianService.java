package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.request.GuardianDTO;
import com.ttps.laboratorio.entity.Guardian;
import com.ttps.laboratorio.repository.IGuardianRepository;
import org.springframework.stereotype.Service;

@Service
public class GuardianService {

  private final IGuardianRepository guardianRepository;

  public GuardianService(IGuardianRepository guardianRepository) {
    this.guardianRepository = guardianRepository;
  }

  public Guardian createGuardian(GuardianDTO request) {
    Guardian guardian = new Guardian();
    guardian.setFirstName(request.getFirstName());
    guardian.setLastName(request.getLastName());
    guardian.setPhoneNumber(request.getPhoneNumber());
    guardian.setEmail(request.getEmail());
    guardian.setAddress(request.getAddress());
    guardianRepository.save(guardian);
    return guardian;
  }

}
