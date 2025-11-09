package com.banking.system.exception;

public class CustomerNotFoundException extends RuntimeException {
	
	public CustomerNotFoundException(Long id) {
		super("Customer not found with ID: " + id);
	}
	
	public CustomerNotFoundException(String email) {
		super("Customer not found with email: " + email);
	}
}

