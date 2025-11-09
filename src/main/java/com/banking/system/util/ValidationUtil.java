package com.banking.system.util;

import java.math.BigDecimal;

/**
 * Utility class for common validation operations
 */
public final class ValidationUtil {
	
	private ValidationUtil() {
		// Utility class - prevent instantiation
	}
	
	/**
	 * Validates if amount is positive
	 */
	public static boolean isPositiveAmount(BigDecimal amount) {
		return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
	}
	
	/**
	 * Validates if amount meets minimum transaction amount
	 */
	public static boolean meetsMinimumAmount(BigDecimal amount) {
		return isPositiveAmount(amount) && 
		       amount.compareTo(BigDecimal.valueOf(Constants.MIN_TRANSACTION_AMOUNT)) >= 0;
	}
	
	/**
	 * Validates if balance is sufficient for withdrawal
	 */
	public static boolean hasSufficientBalance(BigDecimal balance, BigDecimal amount) {
		return balance != null && amount != null && 
		       balance.compareTo(amount) >= 0;
	}
	
	/**
	 * Validates if balance is non-negative
	 */
	public static boolean isNonNegativeBalance(BigDecimal balance) {
		return balance != null && balance.compareTo(BigDecimal.ZERO) >= 0;
	}
	
	/**
	 * Validates email format (basic validation)
	 */
	public static boolean isValidEmail(String email) {
		if (email == null || email.isEmpty()) {
			return false;
		}
		return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
	}
	
	/**
	 * Validates if string is not null or empty
	 */
	public static boolean isNotNullOrEmpty(String value) {
		return value != null && !value.trim().isEmpty();
	}
}

