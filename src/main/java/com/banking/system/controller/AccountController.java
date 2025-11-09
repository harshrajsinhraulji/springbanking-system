package com.banking.system.controller;

import com.banking.system.dto.AccountDTO;
import com.banking.system.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
	
	private final AccountService accountService;
	
	@PostMapping
	public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
		AccountDTO createdAccount = accountService.createAccount(accountDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
		AccountDTO account = accountService.getAccountById(id);
		return ResponseEntity.ok(account);
	}
	
	@GetMapping("/number/{accountNumber}")
	public ResponseEntity<AccountDTO> getAccountByNumber(@PathVariable String accountNumber) {
		AccountDTO account = accountService.getAccountByNumber(accountNumber);
		return ResponseEntity.ok(account);
	}
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<AccountDTO>> getAccountsByCustomerId(@PathVariable Long customerId) {
		List<AccountDTO> accounts = accountService.getAccountsByCustomerId(customerId);
		return ResponseEntity.ok(accounts);
	}
	
	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAllAccounts() {
		List<AccountDTO> accounts = accountService.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AccountDTO> updateAccount(
		@PathVariable Long id, 
		@Valid @RequestBody AccountDTO accountDTO) {
		AccountDTO updatedAccount = accountService.updateAccount(id, accountDTO);
		return ResponseEntity.ok(updatedAccount);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
		accountService.deleteAccount(id);
		return ResponseEntity.noContent().build();
	}
}

