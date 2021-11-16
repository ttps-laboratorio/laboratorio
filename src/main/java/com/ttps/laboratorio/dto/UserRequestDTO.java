package com.ttps.laboratorio.dto;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

	private String username;
	private String email;
	private String password;
}
