package com.banking.system.controller;

import com.banking.system.dto.TransactionDTO;
import com.banking.system.dto.TransferRequestDTO;
import com.banking.system.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
	
	private final TransactionService transactionService;
	
	@PostMapping("/deposit")
	public ResponseEntity<TransactionDTO> deposit(
		@RequestParam String accountNumber,
		@RequestParam BigDecimal amount,
		@RequestParam(required = false) String description) {
		TransactionDTO transaction = transactionService.deposit(accountNumber, amount, description);
		return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
	}
	
	@PostMapping("/withdraw")
	public ResponseEntity<TransactionDTO> withdraw(
		@RequestParam String accountNumber,
		@RequestParam BigDecimal amount,
		@RequestParam(required = false) String description) {
		TransactionDTO transaction = transactionService.withdraw(accountNumber, amount, description);
		return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<TransactionDTO> transfer(@Valid @RequestBody TransferRequestDTO transferRequest) {
		TransactionDTO transaction = transactionService.transfer(transferRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
		TransactionDTO transaction = transactionService.getTransactionById(id);
		return ResponseEntity.ok(transaction);
	}
	
	@GetMapping("/transaction-id/{transactionId}")
	public ResponseEntity<TransactionDTO> getTransactionByTransactionId(@PathVariable String transactionId) {
		TransactionDTO transaction = transactionService.getTransactionByTransactionId(transactionId);
		return ResponseEntity.ok(transaction);
	}
	
	@GetMapping("/account/{accountNumber}")
	public ResponseEntity<List<TransactionDTO>> getTransactionsByAccount(
		@PathVariable String accountNumber,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<TransactionDTO> transactions = transactionService.getTransactionsByAccount(accountNumber, pageable);
		return ResponseEntity.ok(transactions);
	}
	
	@GetMapping("/account/{accountNumber}/statement")
	public ResponseEntity<List<TransactionDTO>> getAccountStatement(
		@PathVariable String accountNumber,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
		List<TransactionDTO> transactions = transactionService.getTransactionsByAccountAndDateRange(
			accountNumber, startDate, endDate);
		return ResponseEntity.ok(transactions);
	}
}

