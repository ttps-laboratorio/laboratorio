package com.ttps.laboratorio.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthInsuranceDTO {

	private long id;

	@NotBlank(message = "Health insurance name is required")
	private String name;

	@NotBlank(message = "Health insurance phone number is required")
	private String phoneNumber;

	@NotBlank(message = "Health insurance email is required")
	@Email(message = "Invalid email")
	private String email;

}
