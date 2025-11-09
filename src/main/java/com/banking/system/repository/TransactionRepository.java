package com.banking.system.repository;

import com.banking.system.model.Account;
import com.banking.system.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	Optional<Transaction> findByTransactionId(String transactionId);
	
	List<Transaction> findByAccount(Account account);
	
	Page<Transaction> findByAccountOrderByCreatedAtDesc(Account account, Pageable pageable);
	
	@Query("SELECT t FROM Transaction t WHERE t.account = :account AND t.createdAt BETWEEN :startDate AND :endDate ORDER BY t.createdAt DESC")
	List<Transaction> findByAccountAndDateRange(
		@Param("account") Account account,
		@Param("startDate") LocalDateTime startDate,
		@Param("endDate") LocalDateTime endDate
	);
	
	@Query("SELECT t FROM Transaction t WHERE (t.account = :account OR t.toAccount = :account) ORDER BY t.createdAt DESC")
	Page<Transaction> findAllTransactionsForAccount(@Param("account") Account account, Pageable pageable);
}

