package com.ttps.laboratorio.auth;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {

	@NotEmpty
	private String username;

	@NotEmpty
	private String password;
}
