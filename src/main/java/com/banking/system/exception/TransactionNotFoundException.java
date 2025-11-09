package com.banking.system.exception;

public class TransactionNotFoundException extends RuntimeException {
	
	public TransactionNotFoundException(Long id) {
		super("Transaction not found with ID: " + id);
	}
	
	public TransactionNotFoundException(String transactionId) {
		super("Transaction not found with transaction ID: " + transactionId);
	}
}

