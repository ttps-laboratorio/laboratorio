package com.ttps.laboratorio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private UserResponseDTO user;
}
