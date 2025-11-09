package com.banking.system.model;

import com.banking.system.util.AccountNumberGenerator;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false, length = 50)
	private String transactionId;
	
	@NotNull(message = "Account is required")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_account_id")
	private Account toAccount;
	
	@NotNull(message = "Transaction type is required")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TransactionType transactionType;
	
	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.01", message = "Amount must be greater than 0")
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;
	
	@Column(length = 500)
	private String description;
	
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal balanceAfterTransaction;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TransactionStatus status = TransactionStatus.COMPLETED;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	public enum TransactionType {
		DEPOSIT, WITHDRAWAL, TRANSFER, INTEREST
	}
	
	public enum TransactionStatus {
		PENDING, COMPLETED, FAILED, CANCELLED
	}
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		if (transactionId == null) {
			transactionId = AccountNumberGenerator.generateTransactionId();
		}
	}
}

