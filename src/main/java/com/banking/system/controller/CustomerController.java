package com.banking.system.controller;

import com.banking.system.dto.CustomerDTO;
import com.banking.system.dto.LoginRequestDTO;
import com.banking.system.dto.LoginResponseDTO;
import com.banking.system.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerService customerService;
	
	@PostMapping
	public ResponseEntity<CustomerDTO> registerCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
		CustomerDTO createdCustomer = customerService.registerCustomer(customerDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
		CustomerDTO customer = customerService.getCustomerById(id);
		return ResponseEntity.ok(customer);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<CustomerDTO> getCustomerByEmail(@PathVariable String email) {
		CustomerDTO customer = customerService.getCustomerByEmail(email);
		return ResponseEntity.ok(customer);
	}
	
	@GetMapping
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
		List<CustomerDTO> customers = customerService.getAllCustomers();
		return ResponseEntity.ok(customers);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CustomerDTO> updateCustomer(
		@PathVariable Long id, 
		@Valid @RequestBody CustomerDTO customerDTO) {
		CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
		return ResponseEntity.ok(updatedCustomer);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		customerService.deleteCustomer(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
		LoginResponseDTO response = customerService.login(loginRequest);
		return ResponseEntity.ok(response);
	}
}

