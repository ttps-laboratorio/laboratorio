package com.ttps.laboratorio.auth;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider tokenProvider;

	@PostMapping(value = "/login")
	public ResponseEntity<LoginResponseDTO> generateAuthenticationToken(@RequestBody Credentials authenticationRequest)
			throws Exception {

		Authentication authentication = authenticate(authenticationRequest.getUsername(),
				authenticationRequest.getPassword());

		final String token = tokenProvider.createToken(authentication);

		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

	private Authentication authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
}
