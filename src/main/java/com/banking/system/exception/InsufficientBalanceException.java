package com.banking.system.exception;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {
	
	public InsufficientBalanceException(BigDecimal balance, BigDecimal amount) {
		super(String.format("Insufficient balance. Current balance: %.2f, Required: %.2f", 
			balance, amount));
	}
	
	public InsufficientBalanceException(String message) {
		super(message);
	}
}

