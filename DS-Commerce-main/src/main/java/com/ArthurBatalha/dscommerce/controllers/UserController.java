package com.ArthurBatalha.dscommerce.controllers;

import com.ArthurBatalha.dscommerce.dto.UserDTO;
import com.ArthurBatalha.dscommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping(value = "/me")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
	public ResponseEntity<UserDTO> getMe() {
		return ResponseEntity.ok(service.getMe());
	}
}
