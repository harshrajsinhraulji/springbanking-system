package com.banking.system.controller;

import com.banking.system.dto.LoginRequestDTO;
import com.banking.system.dto.LoginResponseDTO;
import com.banking.system.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final CustomerService customerService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> adminLogin(@Valid @RequestBody LoginRequestDTO loginRequest) {
		LoginResponseDTO response = customerService.adminLogin(loginRequest);
		return ResponseEntity.ok(response);
	}
}

