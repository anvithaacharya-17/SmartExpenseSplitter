# Smart Expense Splitter

A Java-based console application designed to manage group expenses and simplify the process of splitting bills among friends or roommates. This project follows a **Layered Architecture** (DAO, DTO, and Service layers) for clean and maintainable code.

## 🚀 Features
* **Group Management**: Create and manage different social groups.
* **Expense Tracking**: Record expenses with descriptions and specify who paid.
* **Smart Splitting**: Automatically calculate balances for group members.
* **Database Integration**: Persistent storage using MySQL.

## 🛠️ Technology Stack
* **Language**: Java 17+
* **Database**: MySQL 8.0
* **Driver**: JDBC (MySQL Connector/J)
* **IDE**: Eclipse

## 📋 Database Setup
Before running the application, you must set up the MySQL database. 
1. Open your MySQL terminal or Workbench.
2. Run the provided `db_setup.sql` script:
   ```sql
   source path/to/db_setup.sql;
