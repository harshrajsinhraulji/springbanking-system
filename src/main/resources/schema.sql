-- =====================================================
-- Spring Banking System - Database Schema
-- Database Name: banking_db
-- MySQL Version: 8.0+
-- =====================================================

-- Create database (if not exists)
CREATE DATABASE IF NOT EXISTS banking_db;
USE banking_db;

-- =====================================================
-- Table: roles
-- Description: Stores user roles (ADMIN, CUSTOMER)
-- =====================================================
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT chk_role_name CHECK (name IN ('ADMIN', 'CUSTOMER'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Table: customers
-- Description: Stores customer information
-- =====================================================
CREATE TABLE IF NOT EXISTS customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(15) NOT NULL,
    address VARCHAR(500) NOT NULL,
    password VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    INDEX idx_email (email),
    INDEX idx_phone (phone_number),
    INDEX idx_active (active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Table: customer_roles
-- Description: Many-to-many relationship between customers and roles
-- =====================================================
CREATE TABLE IF NOT EXISTS customer_roles (
    customer_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (customer_id, role_id),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    INDEX idx_customer_id (customer_id),
    INDEX idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Table: accounts
-- Description: Stores bank accounts
-- =====================================================
CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    customer_id BIGINT NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE RESTRICT,
    INDEX idx_account_number (account_number),
    INDEX idx_customer_id (customer_id),
    INDEX idx_active (active),
    CONSTRAINT chk_balance_non_negative CHECK (balance >= 0),
    CONSTRAINT chk_account_type CHECK (account_type IN ('SAVINGS', 'CHECKING', 'CURRENT'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Table: transactions
-- Description: Stores all transaction records
-- =====================================================
CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id VARCHAR(50) NOT NULL UNIQUE,
    account_id BIGINT NOT NULL,
    to_account_id BIGINT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    description VARCHAR(500) NULL,
    balance_after_transaction DECIMAL(19, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'COMPLETED',
    created_at DATETIME NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE RESTRICT,
    FOREIGN KEY (to_account_id) REFERENCES accounts(id) ON DELETE SET NULL,
    INDEX idx_transaction_id (transaction_id),
    INDEX idx_account_id (account_id),
    INDEX idx_to_account_id (to_account_id),
    INDEX idx_created_at (created_at),
    INDEX idx_transaction_type (transaction_type),
    CONSTRAINT chk_amount_positive CHECK (amount > 0),
    CONSTRAINT chk_transaction_type CHECK (transaction_type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER', 'INTEREST')),
    CONSTRAINT chk_status CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED', 'CANCELLED'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- Insert Initial Data: Roles
-- =====================================================
INSERT INTO roles (name) VALUES ('ADMIN') ON DUPLICATE KEY UPDATE name=name;
INSERT INTO roles (name) VALUES ('CUSTOMER') ON DUPLICATE KEY UPDATE name=name;

-- =====================================================
-- Optional: Create indexes for better query performance
-- =====================================================
-- Additional composite indexes for common queries
CREATE INDEX idx_customer_email_active ON customers(email, active);
CREATE INDEX idx_account_customer_active ON accounts(customer_id, active);
CREATE INDEX idx_transaction_account_date ON transactions(account_id, created_at DESC);

-- =====================================================
-- Schema creation complete!
-- =====================================================

