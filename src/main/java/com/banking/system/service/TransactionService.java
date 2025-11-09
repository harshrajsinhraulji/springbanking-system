package com.banking.system.service;

import com.banking.system.dto.TransactionDTO;
import com.banking.system.dto.TransferRequestDTO;
import com.banking.system.exception.AccountNotFoundException;
import com.banking.system.exception.InsufficientBalanceException;
import com.banking.system.exception.TransactionNotFoundException;
import com.banking.system.model.Account;
import com.banking.system.model.Transaction;
import com.banking.system.repository.AccountRepository;
import com.banking.system.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TransactionService {
	
	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;
	
	public TransactionDTO deposit(String accountNumber, BigDecimal amount, String description) {
		log.info("Processing deposit of {} to account: {}", amount, accountNumber);
		
		Account account = accountRepository.findByAccountNumber(accountNumber)
			.orElseThrow(() -> new AccountNotFoundException(accountNumber));
		
		if (!account.getActive()) {
			throw new RuntimeException("Account is not active");
		}
		
		account.setBalance(account.getBalance().add(amount));
		accountRepository.save(account);
		
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setTransactionType(Transaction.TransactionType.DEPOSIT);
		transaction.setAmount(amount);
		transaction.setDescription(description != null ? description : "Deposit");
		transaction.setBalanceAfterTransaction(account.getBalance());
		transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
		
		Transaction savedTransaction = transactionRepository.save(transaction);
		log.info("Deposit completed. Transaction ID: {}", savedTransaction.getTransactionId());
		
		return convertToDTO(savedTransaction);
	}
	
	public TransactionDTO withdraw(String accountNumber, BigDecimal amount, String description) {
		log.info("Processing withdrawal of {} from account: {}", amount, accountNumber);
		
		Account account = accountRepository.findByAccountNumber(accountNumber)
			.orElseThrow(() -> new AccountNotFoundException(accountNumber));
		
		if (!account.getActive()) {
			throw new RuntimeException("Account is not active");
		}
		
		if (account.getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException(account.getBalance(), amount);
		}
		
		account.setBalance(account.getBalance().subtract(amount));
		accountRepository.save(account);
		
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setTransactionType(Transaction.TransactionType.WITHDRAWAL);
		transaction.setAmount(amount);
		transaction.setDescription(description != null ? description : "Withdrawal");
		transaction.setBalanceAfterTransaction(account.getBalance());
		transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
		
		Transaction savedTransaction = transactionRepository.save(transaction);
		log.info("Withdrawal completed. Transaction ID: {}", savedTransaction.getTransactionId());
		
		return convertToDTO(savedTransaction);
	}
	
	public TransactionDTO transfer(TransferRequestDTO transferRequest) {
		log.info("Processing transfer of {} from {} to {}", 
			transferRequest.getAmount(), 
			transferRequest.getFromAccountNumber(), 
			transferRequest.getToAccountNumber());
		
		if (transferRequest.getFromAccountNumber().equals(transferRequest.getToAccountNumber())) {
			throw new RuntimeException("Cannot transfer to the same account");
		}
		
		Account fromAccount = accountRepository.findByAccountNumber(transferRequest.getFromAccountNumber())
			.orElseThrow(() -> new AccountNotFoundException(transferRequest.getFromAccountNumber()));
		
		Account toAccount = accountRepository.findByAccountNumber(transferRequest.getToAccountNumber())
			.orElseThrow(() -> new AccountNotFoundException(transferRequest.getToAccountNumber()));
		
		if (!fromAccount.getActive()) {
			throw new RuntimeException("From account is not active");
		}
		
		if (!toAccount.getActive()) {
			throw new RuntimeException("To account is not active");
		}
		
		if (fromAccount.getBalance().compareTo(transferRequest.getAmount()) < 0) {
			throw new InsufficientBalanceException(fromAccount.getBalance(), transferRequest.getAmount());
		}
		
		// Perform transfer atomically
		fromAccount.setBalance(fromAccount.getBalance().subtract(transferRequest.getAmount()));
		toAccount.setBalance(toAccount.getBalance().add(transferRequest.getAmount()));
		
		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);
		
		// Create transaction record
		Transaction transaction = new Transaction();
		transaction.setAccount(fromAccount);
		transaction.setToAccount(toAccount);
		transaction.setTransactionType(Transaction.TransactionType.TRANSFER);
		transaction.setAmount(transferRequest.getAmount());
		transaction.setDescription(transferRequest.getDescription() != null ? 
			transferRequest.getDescription() : "Transfer to " + toAccount.getAccountNumber());
		transaction.setBalanceAfterTransaction(fromAccount.getBalance());
		transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
		
		Transaction savedTransaction = transactionRepository.save(transaction);
		log.info("Transfer completed. Transaction ID: {}", savedTransaction.getTransactionId());
		
		return convertToDTO(savedTransaction);
	}
	
	@Transactional(readOnly = true)
	public TransactionDTO getTransactionById(Long id) {
		Transaction transaction = transactionRepository.findById(id)
			.orElseThrow(() -> new TransactionNotFoundException(id));
		return convertToDTO(transaction);
	}
	
	@Transactional(readOnly = true)
	public TransactionDTO getTransactionByTransactionId(String transactionId) {
		Transaction transaction = transactionRepository.findByTransactionId(transactionId)
			.orElseThrow(() -> new TransactionNotFoundException(transactionId));
		return convertToDTO(transaction);
	}
	
	@Transactional(readOnly = true)
	public List<TransactionDTO> getTransactionsByAccount(String accountNumber, Pageable pageable) {
		Account account = accountRepository.findByAccountNumber(accountNumber)
			.orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));
		
		Page<Transaction> transactions = transactionRepository.findAllTransactionsForAccount(account, pageable);
		return transactions.getContent().stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<TransactionDTO> getTransactionsByAccountAndDateRange(
		String accountNumber, 
		LocalDateTime startDate, 
		LocalDateTime endDate) {
		
		Account account = accountRepository.findByAccountNumber(accountNumber)
			.orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));
		
		List<Transaction> transactions = transactionRepository.findByAccountAndDateRange(account, startDate, endDate);
		return transactions.stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}
	
	private TransactionDTO convertToDTO(Transaction transaction) {
		TransactionDTO dto = new TransactionDTO();
		dto.setId(transaction.getId());
		dto.setTransactionId(transaction.getTransactionId());
		dto.setAccountId(transaction.getAccount().getId());
		dto.setAccountNumber(transaction.getAccount().getAccountNumber());
		
		if (transaction.getToAccount() != null) {
			dto.setToAccountId(transaction.getToAccount().getId());
			dto.setToAccountNumber(transaction.getToAccount().getAccountNumber());
		}
		
		dto.setTransactionType(transaction.getTransactionType());
		dto.setAmount(transaction.getAmount());
		dto.setDescription(transaction.getDescription());
		dto.setBalanceAfterTransaction(transaction.getBalanceAfterTransaction());
		dto.setStatus(transaction.getStatus());
		dto.setCreatedAt(transaction.getCreatedAt());
		return dto;
	}
}

