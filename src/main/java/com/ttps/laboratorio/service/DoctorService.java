package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.request.DoctorDTO;
import com.ttps.laboratorio.entity.Doctor;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IDoctorRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

	private final IDoctorRepository doctorRepository;

	public DoctorService(IDoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}

	public Doctor getDoctor(Long id) {
		return this.doctorRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un doctor con el id" + id + "."));
	}

	/**
	 * Gets all doctors registered.
	 *
	 * @return List of all the doctors
	 */
	public List<Doctor> getAllDoctors() {
		return new ArrayList<>(doctorRepository.findAll());
	}

	/**
	 * Creates new doctor.
	 *
	 * @param request doctor information
	 */
	public Doctor createDoctor(DoctorDTO request) {
		Doctor doctor = new Doctor();
		doctor.setFirstName(request.getFirstName());
		doctor.setLastName(request.getLastName());
		doctor.setEmail(request.getEmail());
		doctor.setPhoneNumber(request.getPhoneNumber());
		doctor.setLicenseNumber(request.getLicenseNumber());
		return doctorRepository.save(doctor);
	}

	/**
	 * Updates an existing doctor.
	 *
	 * @param doctorID id from the doctor to search
	 * @param request  new data to change
	 */
	public Doctor updateDoctor(Long doctorID, DoctorDTO request) {
		Doctor doctor = doctorRepository.findById(doctorID)
				.orElseThrow(() -> new NotFoundException("No existe un doctor con el id " + doctorID + "."));
		doctor.setFirstName(request.getFirstName());
		doctor.setLastName(request.getLastName());
		doctor.setEmail(request.getEmail());
		doctor.setPhoneNumber(request.getPhoneNumber());
		doctor.setLicenseNumber(request.getLicenseNumber());
		return doctorRepository.save(doctor);
	}

}
