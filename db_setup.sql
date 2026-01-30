-- 1. Create the database
CREATE DATABASE IF NOT EXISTS expense_splitter;
USE expense_splitter;

-- 2. Create the 'groups' table
-- IMPORTANT: 'groups' is a reserved keyword in MySQL.
-- We use backticks (``) so the Java DAO can access it without errors.
CREATE TABLE IF NOT EXISTS `groups` (
    group_id INT PRIMARY KEY AUTO_INCREMENT,
    group_name VARCHAR(50) NOT NULL UNIQUE
);

-- 3. Create the 'expenses' table
CREATE TABLE IF NOT EXISTS expenses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    group_id INT NOT NULL,
    description VARCHAR(100) NOT NULL,
    amount DOUBLE NOT NULL,
    payer VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (group_id) REFERENCES `groups`(group_id) ON DELETE CASCADE
);
