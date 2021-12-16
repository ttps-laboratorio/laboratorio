package com.ttps.laboratorio.dto.response;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponseDTO {

	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Integer licenseNumber;

}
