package com.banking.system.service;

import com.banking.system.dto.TransferRequestDTO;
import com.banking.system.model.Account;
import com.banking.system.model.Transaction;
import com.banking.system.repository.AccountRepository;
import com.banking.system.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@Mock
	private AccountRepository accountRepository;
	
	@InjectMocks
	private TransactionService transactionService;
	
	private Account account1;
	private Account account2;
	
	@BeforeEach
	void setUp() {
		account1 = new Account();
		account1.setId(1L);
		account1.setAccountNumber("ACC123");
		account1.setBalance(new BigDecimal("1000.00"));
		account1.setActive(true);
		
		account2 = new Account();
		account2.setId(2L);
		account2.setAccountNumber("ACC456");
		account2.setBalance(new BigDecimal("500.00"));
		account2.setActive(true);
	}
	
	@Test
	void testDeposit_Success() {
		when(accountRepository.findByAccountNumber("ACC123")).thenReturn(Optional.of(account1));
		when(accountRepository.save(any(Account.class))).thenReturn(account1);
		when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
			Transaction t = invocation.getArgument(0);
			t.setId(1L);
			t.setTransactionId("TXN123");
			return t;
		});
		
		var result = transactionService.deposit("ACC123", new BigDecimal("100.00"), "Test deposit");
		
		assertNotNull(result);
		assertEquals("TXN123", result.getTransactionId());
		verify(accountRepository, times(1)).save(any(Account.class));
		verify(transactionRepository, times(1)).save(any(Transaction.class));
	}
	
	@Test
	void testWithdraw_Success() {
		when(accountRepository.findByAccountNumber("ACC123")).thenReturn(Optional.of(account1));
		when(accountRepository.save(any(Account.class))).thenReturn(account1);
		when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
			Transaction t = invocation.getArgument(0);
			t.setId(1L);
			t.setTransactionId("TXN123");
			return t;
		});
		
		var result = transactionService.withdraw("ACC123", new BigDecimal("100.00"), "Test withdrawal");
		
		assertNotNull(result);
		assertEquals("TXN123", result.getTransactionId());
		verify(accountRepository, times(1)).save(any(Account.class));
	}
	
	@Test
	void testWithdraw_InsufficientBalance() {
		when(accountRepository.findByAccountNumber("ACC123")).thenReturn(Optional.of(account1));
		
		assertThrows(RuntimeException.class, () -> {
			transactionService.withdraw("ACC123", new BigDecimal("2000.00"), "Test withdrawal");
		});
	}
	
	@Test
	void testTransfer_Success() {
		TransferRequestDTO transferRequest = new TransferRequestDTO();
		transferRequest.setFromAccountNumber("ACC123");
		transferRequest.setToAccountNumber("ACC456");
		transferRequest.setAmount(new BigDecimal("200.00"));
		transferRequest.setDescription("Test transfer");
		
		when(accountRepository.findByAccountNumber("ACC123")).thenReturn(Optional.of(account1));
		when(accountRepository.findByAccountNumber("ACC456")).thenReturn(Optional.of(account2));
		when(accountRepository.save(any(Account.class))).thenReturn(account1);
		when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
			Transaction t = invocation.getArgument(0);
			t.setId(1L);
			t.setTransactionId("TXN123");
			return t;
		});
		
		var result = transactionService.transfer(transferRequest);
		
		assertNotNull(result);
		assertEquals("TXN123", result.getTransactionId());
		verify(accountRepository, times(2)).save(any(Account.class));
	}
	
	@Test
	void testTransfer_SameAccount() {
		TransferRequestDTO transferRequest = new TransferRequestDTO();
		transferRequest.setFromAccountNumber("ACC123");
		transferRequest.setToAccountNumber("ACC123");
		transferRequest.setAmount(new BigDecimal("200.00"));
		
		assertThrows(RuntimeException.class, () -> {
			transactionService.transfer(transferRequest);
		});
	}
}

