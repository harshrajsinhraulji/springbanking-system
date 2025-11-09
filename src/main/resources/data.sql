-- =====================================================
-- Spring Banking System - Initial Data
-- This file is executed after schema.sql
-- =====================================================

-- Insert default roles if they don't exist
INSERT IGNORE INTO roles (name) VALUES ('ADMIN');
INSERT IGNORE INTO roles (name) VALUES ('CUSTOMER');

-- =====================================================
-- Sample Data (Optional - for development/testing)
-- Uncomment below to insert sample data
-- =====================================================

-- Sample Admin Customer
-- INSERT INTO customers (first_name, last_name, email, phone_number, address, password, active, created_at, updated_at)
-- VALUES ('Admin', 'User', 'admin@banking.com', '1234567890', '123 Admin Street', 'admin123', TRUE, NOW(), NOW());

-- Sample Regular Customer
-- INSERT INTO customers (first_name, last_name, email, phone_number, address, password, active, created_at, updated_at)
-- VALUES ('John', 'Doe', 'john.doe@example.com', '9876543210', '456 Main Street', 'password123', TRUE, NOW(), NOW());

-- Assign ADMIN role to admin customer
-- INSERT INTO customer_roles (customer_id, role_id)
-- SELECT c.id, r.id
-- FROM customers c, roles r
-- WHERE c.email = 'admin@banking.com' AND r.name = 'ADMIN';

-- Assign CUSTOMER role to regular customers
-- INSERT INTO customer_roles (customer_id, role_id)
-- SELECT c.id, r.id
-- FROM customers c, roles r
-- WHERE c.email IN ('john.doe@example.com') AND r.name = 'CUSTOMER';

-- =====================================================
-- Note: In production, remove or comment out sample data
-- =====================================================

