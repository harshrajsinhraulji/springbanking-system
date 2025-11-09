package com.banking.system.dto;

import com.banking.system.model.Account;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
	
	private Long id;
	
	private String accountNumber;
	
	@NotNull(message = "Customer ID is required")
	private Long customerId;
	
	private String customerName;
	
	@NotNull(message = "Account type is required")
	private Account.AccountType accountType;
	
	private BigDecimal balance;
	
	private Boolean active;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
}

