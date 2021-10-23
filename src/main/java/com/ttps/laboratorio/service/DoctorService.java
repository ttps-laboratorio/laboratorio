package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.DoctorDTO;
import com.ttps.laboratorio.entity.Doctor;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IDoctorRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

  private final IDoctorRepository doctorRepository;

  public DoctorService (IDoctorRepository doctorRepository) {
    this.doctorRepository = doctorRepository;
  }

  /**
   * Gets all doctors registered.
   * @return List of all the doctors
   */
  public List<Doctor> getAllDoctors() {
    return new ArrayList<>(doctorRepository.findAll());
  }

  /**
   * Creates new doctor.
   * @param request doctor information
   */
  public void createDoctor(DoctorDTO request) {
    Doctor doctor = new Doctor();
    doctor.setFirstName(request.getFirstName());
    doctor.setLastName(request.getLastName());
    doctor.setEmail(request.getEmail());
    doctor.setPhoneNumber(request.getPhoneNumber());
    doctor.setLicenseNumber(request.getLicenseNumber());
    doctorRepository.save(doctor);
  }

  /**
   * Updates an existing doctor.
   * @param doctorID id from the doctor to search
   * @param request new data to change
   */
  public void updateDoctor(Long doctorID, DoctorDTO request) {
    Doctor doctor = doctorRepository.findById(doctorID)
        .orElseThrow(() -> new NotFoundException("A doctor with the id " + doctorID + " does not exist."));
    doctor.setFirstName(request.getFirstName());
    doctor.setLastName(request.getLastName());
    doctor.setEmail(request.getEmail());
    doctor.setPhoneNumber(request.getPhoneNumber());
    doctor.setLicenseNumber(request.getLicenseNumber());
    doctorRepository.save(doctor);
  }

}
