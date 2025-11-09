package com.banking.system.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDTO {
	
	@NotBlank(message = "Source account number is required")
	private String fromAccountNumber;
	
	@NotBlank(message = "Destination account number is required")
	private String toAccountNumber;
	
	@NotNull(message = "Transfer amount is required")
	@DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
	private BigDecimal amount;
	
	private String description;
}

