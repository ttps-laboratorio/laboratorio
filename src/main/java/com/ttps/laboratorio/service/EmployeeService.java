package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.request.EmployeeRequestDTO;
import com.ttps.laboratorio.dto.request.UserRequestDTO;
import com.ttps.laboratorio.dto.response.EmployeeResponseDTO;
import com.ttps.laboratorio.entity.Employee;
import com.ttps.laboratorio.entity.RoleEnum;
import com.ttps.laboratorio.entity.User;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IEmployeeRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	private final IEmployeeRepository employeeRepository;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public EmployeeService(IEmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
		super();
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public EmployeeResponseDTO get(Long id) {
		Employee employee = this.employeeRepository.findById(id).orElseThrow();
		return new ModelMapper().map(employee, EmployeeResponseDTO.class);
	}

	public List<EmployeeResponseDTO> getAll() {
		return employeeRepository.findAll().stream().map(e -> new ModelMapper().map(e, EmployeeResponseDTO.class))
				.collect(Collectors.toList());
	}

	public EmployeeResponseDTO create(EmployeeRequestDTO request) {
		UserRequestDTO userDTO = request.getUser();
		User user = new User(null, userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()),
				userDTO.getEmail(), RoleEnum.EMPLOYEE);
		Employee employee = new Employee(null, request.getFirstName(), request.getLastName(), user);
		employee = employeeRepository.save(employee);
		return new ModelMapper().map(employee, EmployeeResponseDTO.class);
	}

	public EmployeeResponseDTO update(Long employeeId, EmployeeRequestDTO request) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new NotFoundException("No existe un empleado con el id " + employeeId + "."));
		employee.setFirstName(request.getFirstName());
		employee.setLastName(request.getLastName());
		UserRequestDTO userDTO = request.getUser();
		User user = employee.getUser();
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		employee = employeeRepository.save(employee);
		return new ModelMapper().map(employee, EmployeeResponseDTO.class);
	}

	public Employee getByUser(User user) {
		return this.employeeRepository.findByUser(user);
	}

}
