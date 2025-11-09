package com.banking.system.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Utility class for generating unique account numbers
 */
public final class AccountNumberGenerator {
	
	private static final Random RANDOM = new Random();
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	
	private AccountNumberGenerator() {
		// Utility class - prevent instantiation
	}
	
	/**
	 * Generates a unique account number
	 * Format: ACC + timestamp + random 4-digit number
	 * Example: ACC2025111000123456789
	 */
	public static String generate() {
		String timestamp = LocalDateTime.now().format(FORMATTER);
		String randomSuffix = String.format("%04d", RANDOM.nextInt(10000));
		return Constants.ACCOUNT_PREFIX + timestamp + randomSuffix;
	}
	
	/**
	 * Generates a unique transaction ID
	 * Format: TXN + timestamp + random 6-digit number
	 * Example: TXN202511100012345678901234
	 */
	public static String generateTransactionId() {
		String timestamp = LocalDateTime.now().format(FORMATTER);
		String randomSuffix = String.format("%06d", RANDOM.nextInt(1000000));
		return Constants.TRANSACTION_PREFIX + timestamp + randomSuffix;
	}
	
	/**
	 * Validates account number format
	 */
	public static boolean isValidAccountNumber(String accountNumber) {
		if (accountNumber == null || accountNumber.length() != Constants.ACCOUNT_NUMBER_LENGTH) {
			return false;
		}
		return accountNumber.startsWith(Constants.ACCOUNT_PREFIX);
	}
}

