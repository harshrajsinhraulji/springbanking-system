package com.banking.system.util;

/**
 * Application-wide constants
 */
public final class Constants {
	
	private Constants() {
		// Utility class - prevent instantiation
	}
	
	// Account Constants
	public static final String ACCOUNT_PREFIX = "ACC";
	public static final int ACCOUNT_NUMBER_LENGTH = 20;
	public static final double MIN_ACCOUNT_BALANCE = 0.0;
	public static final double MIN_TRANSACTION_AMOUNT = 0.01;
	
	// Transaction Constants
	public static final String TRANSACTION_PREFIX = "TXN";
	public static final int TRANSACTION_ID_LENGTH = 50;
	
	// Customer Constants
	public static final int MIN_PASSWORD_LENGTH = 6;
	public static final int MAX_PASSWORD_LENGTH = 100;
	public static final int PHONE_NUMBER_MIN_LENGTH = 10;
	public static final int PHONE_NUMBER_MAX_LENGTH = 15;
	
	// Validation Messages
	public static final String INSUFFICIENT_BALANCE = "Insufficient balance";
	public static final String ACCOUNT_NOT_FOUND = "Account not found";
	public static final String CUSTOMER_NOT_FOUND = "Customer not found";
	public static final String TRANSACTION_NOT_FOUND = "Transaction not found";
	public static final String ACCOUNT_INACTIVE = "Account is not active";
	public static final String SAME_ACCOUNT_TRANSFER = "Cannot transfer to the same account";
	
	// Default Values
	public static final boolean DEFAULT_ACCOUNT_ACTIVE = true;
	public static final boolean DEFAULT_CUSTOMER_ACTIVE = true;
	
	// Date/Time Formats
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	// API Response Messages
	public static final String SUCCESS_MESSAGE = "Operation completed successfully";
	public static final String ERROR_MESSAGE = "An error occurred";
	public static final String VALIDATION_ERROR = "Validation failed";
}

