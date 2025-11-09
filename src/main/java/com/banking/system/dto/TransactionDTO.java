package com.banking.system.dto;

import com.banking.system.model.Transaction;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
	
	private Long id;
	
	private String transactionId;
	
	@NotNull(message = "Account ID is required")
	private Long accountId;
	
	private String accountNumber;
	
	private Long toAccountId;
	
	private String toAccountNumber;
	
	@NotNull(message = "Transaction type is required")
	private Transaction.TransactionType transactionType;
	
	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.01", message = "Amount must be greater than 0")
	private BigDecimal amount;
	
	private String description;
	
	private BigDecimal balanceAfterTransaction;
	
	private Transaction.TransactionStatus status;
	
	private LocalDateTime createdAt;
}

