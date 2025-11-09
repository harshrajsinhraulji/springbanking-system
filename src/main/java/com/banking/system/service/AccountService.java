package com.banking.system.service;

import com.banking.system.dto.AccountDTO;
import com.banking.system.exception.AccountNotFoundException;
import com.banking.system.exception.CustomerNotFoundException;
import com.banking.system.model.Account;
import com.banking.system.model.Customer;
import com.banking.system.repository.AccountRepository;
import com.banking.system.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountService {
	
	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;
	
	public AccountDTO createAccount(AccountDTO accountDTO) {
		log.info("Creating account for customer ID: {}", accountDTO.getCustomerId());
		
		Customer customer = customerRepository.findById(accountDTO.getCustomerId())
			.orElseThrow(() -> new CustomerNotFoundException(accountDTO.getCustomerId()));
		
		Account account = new Account();
		account.setCustomer(customer);
		account.setAccountType(accountDTO.getAccountType());
		account.setBalance(accountDTO.getBalance() != null ? accountDTO.getBalance() : java.math.BigDecimal.ZERO);
		account.setActive(true);
		
		Account savedAccount = accountRepository.save(account);
		log.info("Account created successfully with number: {}", savedAccount.getAccountNumber());
		
		return convertToDTO(savedAccount);
	}
	
	@Transactional(readOnly = true)
	public AccountDTO getAccountById(Long id) {
		Account account = accountRepository.findById(id)
			.orElseThrow(() -> new AccountNotFoundException(id));
		return convertToDTO(account);
	}
	
	@Transactional(readOnly = true)
	public AccountDTO getAccountByNumber(String accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber)
			.orElseThrow(() -> new AccountNotFoundException(accountNumber));
		return convertToDTO(account);
	}
	
	@Transactional(readOnly = true)
	public List<AccountDTO> getAccountsByCustomerId(Long customerId) {
		return accountRepository.findByCustomerId(customerId).stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<AccountDTO> getAllAccounts() {
		return accountRepository.findAll().stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}
	
	public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {
		log.info("Updating account with ID: {}", id);
		
		Account account = accountRepository.findById(id)
			.orElseThrow(() -> new AccountNotFoundException(id));
		
		if (accountDTO.getAccountType() != null) {
			account.setAccountType(accountDTO.getAccountType());
		}
		if (accountDTO.getActive() != null) {
			account.setActive(accountDTO.getActive());
		}
		
		Account updatedAccount = accountRepository.save(account);
		log.info("Account updated successfully with ID: {}", updatedAccount.getId());
		
		return convertToDTO(updatedAccount);
	}
	
	public void deleteAccount(Long id) {
		log.info("Deleting account with ID: {}", id);
		
		Account account = accountRepository.findById(id)
			.orElseThrow(() -> new AccountNotFoundException(id));
		
		if (account.getBalance().compareTo(java.math.BigDecimal.ZERO) > 0) {
			throw new RuntimeException("Cannot delete account with balance. Please withdraw all funds first.");
		}
		
		account.setActive(false);
		accountRepository.save(account);
		log.info("Account deactivated successfully with ID: {}", id);
	}
	
	@Transactional(readOnly = true)
	public Account getAccountEntity(String accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber)
			.orElseThrow(() -> new AccountNotFoundException(accountNumber));
	}
	
	private AccountDTO convertToDTO(Account account) {
		AccountDTO dto = new AccountDTO();
		dto.setId(account.getId());
		dto.setAccountNumber(account.getAccountNumber());
		dto.setCustomerId(account.getCustomer().getId());
		dto.setCustomerName(account.getCustomer().getFirstName() + " " + account.getCustomer().getLastName());
		dto.setAccountType(account.getAccountType());
		dto.setBalance(account.getBalance());
		dto.setActive(account.getActive());
		dto.setCreatedAt(account.getCreatedAt());
		dto.setUpdatedAt(account.getUpdatedAt());
		return dto;
	}
}

