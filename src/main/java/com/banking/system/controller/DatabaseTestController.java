package com.banking.system.controller;

import com.banking.system.model.Role;
import com.banking.system.repository.CustomerRepository;
import com.banking.system.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class DatabaseTestController {
	
	private final CustomerRepository customerRepository;
	private final RoleRepository roleRepository;
	
	@GetMapping("/connection")
	public ResponseEntity<Map<String, Object>> testConnection() {
		Map<String, Object> response = new HashMap<>();
		
		try {
			// Test 1: Count customers
			long customerCount = customerRepository.count();
			
			// Test 2: Count roles
			long roleCount = roleRepository.count();
			
			// Test 3: Try to find roles
			boolean hasAdminRole = roleRepository.findByName(Role.RoleType.ADMIN).isPresent();
			boolean hasCustomerRole = roleRepository.findByName(Role.RoleType.CUSTOMER).isPresent();
			
			response.put("status", "SUCCESS");
			response.put("message", "Database connection and queries working!");
			response.put("customerCount", customerCount);
			response.put("roleCount", roleCount);
			response.put("hasAdminRole", hasAdminRole);
			response.put("hasCustomerRole", hasCustomerRole);
			response.put("database", "Railway MySQL");
			response.put("connection", "ACTIVE");
			
			return ResponseEntity.ok(response);
			
		} catch (Exception e) {
			response.put("status", "FAILED");
			response.put("error", e.getMessage());
			response.put("message", "Database test failed!");
			return ResponseEntity.status(500).body(response);
		}
	}
}

