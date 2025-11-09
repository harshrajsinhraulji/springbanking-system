package com.banking.system.exception;

public class DuplicateResourceException extends RuntimeException {
	
	public DuplicateResourceException(String resource, String value) {
		super(String.format("%s with %s already exists", resource, value));
	}
	
	public DuplicateResourceException(String message) {
		super(message);
	}
}

