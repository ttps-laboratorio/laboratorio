package com.ttps.laboratorio.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDTO {

	@NotBlank(message = "Employee first name is required")
	private String firstName;

	@NotBlank(message = "Employee last name is required")
	private String lastName;

	@NotNull(message = "Employee user is required")
	private UserRequestDTO user;

}
