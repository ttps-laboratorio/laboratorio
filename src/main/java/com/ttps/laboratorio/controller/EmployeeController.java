package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.request.EmployeeRequestDTO;
import com.ttps.laboratorio.dto.response.EmployeeResponseDTO;
import com.ttps.laboratorio.service.EmployeeService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "employee")
public class EmployeeController {

	private final EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PreAuthorize("hasRole('ADMINISTRATOR') OR hasRole('EMPLOYEE')")
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponseDTO> get(@PathVariable(name = "id") @NonNull Long employeeId) {
		return ResponseEntity.ok(employeeService.get(employeeId));
	}

	@PreAuthorize("hasRole('ADMINISTRATOR') OR hasRole('EMPLOYEE')")
	@GetMapping()
	public ResponseEntity<List<EmployeeResponseDTO>> list() {
		return ResponseEntity.ok(employeeService.getAll());
	}

	@PreAuthorize("hasRole('ADMINISTRATOR')")
	@PostMapping()
	public ResponseEntity<EmployeeResponseDTO> create(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
		EmployeeResponseDTO dto = employeeService.create(employeeRequestDTO);
		return ResponseEntity.ok(dto);
	}

	@PreAuthorize("hasRole('ADMINISTRATOR')")
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable(name = "id") @NonNull Long employeeId,
																													@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
		EmployeeResponseDTO dto = employeeService.update(employeeId, employeeRequestDTO);
		return ResponseEntity.ok(dto);
	}
}
