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
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false, length = 20)
	private String accountNumber;
	
	@NotNull(message = "Customer is required")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	@NotNull(message = "Account type is required")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private AccountType accountType;
	
	@DecimalMin(value = "0.0", message = "Balance cannot be negative")
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal balance = BigDecimal.ZERO;
	
	@Column(nullable = false)
	private Boolean active = true;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	public enum AccountType {
		SAVINGS, CHECKING, CURRENT
	}
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
		if (accountNumber == null) {
			accountNumber = generateAccountNumber();
		}
	}
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
	
	private String generateAccountNumber() {
		return AccountNumberGenerator.generate();
	}
}

