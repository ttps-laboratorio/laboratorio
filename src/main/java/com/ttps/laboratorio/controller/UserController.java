package com.ttps.laboratorio.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping
	public ResponseEntity<?> me() {
		return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}

}
