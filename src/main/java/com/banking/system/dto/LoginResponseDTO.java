package com.banking.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
	private String token;
	private CustomerDTO user;
	
	// For simplicity, we'll use a simple token (in production, use JWT)
	// The token will be the customer ID encoded
}

