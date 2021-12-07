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
public class ContactDTO {

	@NotBlank(message = "Contact name is required")
	private String name;

	@NotBlank(message = "Contact phone number is required")
	private String phoneNumber;

	@NotBlank(message = "Contact email is required")
	@Email(message = "Invalid email")
	private String email;

}
