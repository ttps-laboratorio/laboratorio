package com.ttps.laboratorio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ttps.laboratorio.auth.CustomAuthenticationProvider;
import com.ttps.laboratorio.dto.request.PatientDTO;
import com.ttps.laboratorio.dto.request.PatientUserDTO;
import com.ttps.laboratorio.dto.request.UserRequestDTO;
import com.ttps.laboratorio.entity.Patient;
import com.ttps.laboratorio.entity.RoleEnum;
import com.ttps.laboratorio.entity.User;
import com.ttps.laboratorio.exception.BadRequestException;
import com.ttps.laboratorio.exception.LaboratoryException;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IPatientRepository;

@Service
public class PatientService {

	private final GuardianService guardianService;

	private final HealthInsuranceService healthInsuranceService;

	private final IPatientRepository patientRepository;

	private final CustomAuthenticationProvider customAuthenticationProvider;

	private final UserService userService;

	public PatientService(IPatientRepository patientRepository, HealthInsuranceService healthInsuranceService,
												GuardianService guardianService, UserService userService,
												CustomAuthenticationProvider customAuthenticationProvider) {
		this.patientRepository = patientRepository;
		this.healthInsuranceService = healthInsuranceService;
		this.guardianService = guardianService;
		this.userService = userService;
		this.customAuthenticationProvider = customAuthenticationProvider;
	}

	public Patient getPatient(Long id) {
		validateLoggedPatient(id);
		return this.patientRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un paciente con el id " + id + "."));
	}

	public void validateLoggedPatient(Long id) {
		User user = userService.getLoggedUser();
		if (RoleEnum.PATIENT.equals(user.getRole()) && !getByUser(user).getId().equals(id)) {
			throw new BadRequestException("No puede acceder a esta seccion.");
		}
	}

	/**
	 * Gets all patients registered.
	 *
	 * @return List of all the patients
	 */
	public List<Patient> getAllPatients() {
		return new ArrayList<>(patientRepository.findAll());
	}

	/**
	 * Creates new patient.
	 *
	 * @param request patient information
	 */
	@Transactional(rollbackFor = {LaboratoryException.class, Exception.class})
	public Patient createPatient(PatientDTO request) {
		if (patientRepository.existsByDni(request.getDni())) {
			throw new BadRequestException("Existe otro paciente con dni " + request.getDni());
		}
		Patient patient = new Patient();
		if (request.getUser() != null) {
			UserRequestDTO userDTO = request.getUser();
			User user = new User(null, userDTO.getUsername(), customAuthenticationProvider.getPasswordEncoder().encode(userDTO.getPassword()),
					userDTO.getEmail(), RoleEnum.PATIENT);
			patient.setUser(user);
		}
		return setPatient(patient, request);
	}

	/**
	 * Updates an existing patient.
	 *
	 * @param patientID id from the patient to search
	 * @param request   new data to change
	 */
	public void updatePatient(Long patientID, PatientDTO request) {
		Patient patient = patientRepository.findById(patientID)
				.orElseThrow(() -> new NotFoundException("No existe un paciente con el id " + patientID + "."));
		if (patientRepository.existsByDniAndIdNot(request.getDni(), patientID)) {
			throw new BadRequestException("Existe otro paciente con dni " + request.getDni());
		}
		if (request.getUser() != null) {
			UserRequestDTO userDTO = request.getUser();
			User user = patient.getUser();
			user.setUsername(userDTO.getUsername());
			user.setEmail(userDTO.getEmail());
			if (userDTO.getPassword() != null)
				user.setPassword(customAuthenticationProvider.getPasswordEncoder().encode(userDTO.getPassword()));
		}
		setPatient(patient, request);
	}

	private Patient setPatient(Patient patient, PatientDTO dto) {
		patient.setDni(dto.getDni());
		patient.setFirstName(dto.getFirstName());
		patient.setLastName(dto.getLastName());
		patient.setBirthDate(dto.getBirthDate());
		patient.setClinicHistory(dto.getClinicHistory());
		if (patient.isAdult()) {
			if (dto.getEmail() == null || dto.getAddress() == null || dto.getPhoneNumber() == null) {
				throw new BadRequestException("Debe ingresar email, direccion y telefono.");
			}
			patient.setEmail(dto.getEmail());
			patient.setPhoneNumber(dto.getPhoneNumber());
			patient.setAddress(dto.getAddress());
		} else {
			if (dto.getGuardian() == null) {
				throw new BadRequestException("Debe ingresar datos del tutor.");
			}
			patient.setGuardian(guardianService.createGuardian(dto.getGuardian()));
		}
		if (dto.getHealthInsurance() != null) {
			if (dto.getAffiliateNumber() == null) {
				throw new BadRequestException("Debe ingresar el numero de afiliado de la obra social.");
			}
			patient.setHealthInsurance(healthInsuranceService.getHealthInsurance(dto.getHealthInsurance().getId()));
			patient.setAffiliateNumber(dto.getAffiliateNumber());
		} else {
			patient.setHealthInsurance(null);
			patient.setAffiliateNumber(null);
		}
		return patientRepository.save(patient);
	}

	public Patient getByUser(User user) {
		return patientRepository.findByUser(user);
	}


	@Transactional(rollbackFor = {LaboratoryException.class, Exception.class})
	public Patient signUpPatient(PatientUserDTO request) {
		if (!patientRepository.existsByDni(request.getDni().longValue())) {
			throw new BadRequestException("No existe un paciente con el dni " + request.getDni());
		}
		Patient patient = patientRepository.findByDni(request.getDni().longValue());
		if (patient.getUser() != null) {
			throw new BadRequestException("El paciente con dni " + request.getDni() + " ya se encuentra registrado.");
		}
		UserRequestDTO userDTO = request.getUser();
		User user = new User(null, userDTO.getUsername(), customAuthenticationProvider.getPasswordEncoder().encode(userDTO.getPassword()),
				userDTO.getEmail(), RoleEnum.PATIENT);
		patient.setUser(user);
		return patient;
	}
}
