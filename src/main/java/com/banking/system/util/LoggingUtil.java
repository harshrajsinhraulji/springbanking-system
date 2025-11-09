package com.banking.system.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for centralized logging operations
 */
@Slf4j
public final class LoggingUtil {
	
	private LoggingUtil() {
		// Utility class - prevent instantiation
	}
	
	/**
	 * Logs customer operations
	 */
	public static void logCustomerOperation(String operation, Long customerId, String details) {
		log.info("Customer {} - ID: {}, Details: {}", operation, customerId, details);
	}
	
	/**
	 * Logs account operations
	 */
	public static void logAccountOperation(String operation, String accountNumber, String details) {
		log.info("Account {} - Number: {}, Details: {}", operation, accountNumber, details);
	}
	
	/**
	 * Logs transaction operations
	 */
	public static void logTransactionOperation(String operation, String transactionId, String details) {
		log.info("Transaction {} - ID: {}, Details: {}", operation, transactionId, details);
	}
	
	/**
	 * Logs errors with context
	 */
	public static void logError(String context, Exception e) {
		log.error("Error in {}: {}", context, e.getMessage(), e);
	}
	
	/**
	 * Logs database operations
	 */
	public static void logDatabaseOperation(String operation, String entity, Object identifier) {
		log.debug("Database {} - Entity: {}, Identifier: {}", operation, entity, identifier);
	}
}

