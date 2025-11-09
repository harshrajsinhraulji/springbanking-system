package com.banking.system.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for password hashing and validation
 * Note: In production, use BCrypt or Argon2 instead
 */
public final class PasswordUtil {
	
	private static final SecureRandom RANDOM = new SecureRandom();
	private static final String ALGORITHM = "SHA-256";
	
	private PasswordUtil() {
		// Utility class - prevent instantiation
	}
	
	/**
	 * Hashes a password using SHA-256
	 * Note: This is a simple implementation. In production, use BCrypt or Argon2
	 */
	public static String hashPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
			byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Password hashing algorithm not available", e);
		}
	}
	
	/**
	 * Validates password strength
	 */
	public static boolean isValidPassword(String password) {
		if (password == null) {
			return false;
		}
		int length = password.length();
		return length >= Constants.MIN_PASSWORD_LENGTH && 
		       length <= Constants.MAX_PASSWORD_LENGTH;
	}
	
	/**
	 * Generates a random salt
	 */
	public static String generateSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}
	
	/**
	 * Validates phone number format
	 */
	public static boolean isValidPhoneNumber(String phoneNumber) {
		if (phoneNumber == null) {
			return false;
		}
		// Remove any non-digit characters
		String digitsOnly = phoneNumber.replaceAll("[^0-9]", "");
		return digitsOnly.length() >= Constants.PHONE_NUMBER_MIN_LENGTH && 
		       digitsOnly.length() <= Constants.PHONE_NUMBER_MAX_LENGTH;
	}
}

