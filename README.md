# Inventory & Billing System (Core Java + JDBC)

A console-based Inventory & Billing System built using **Core Java**, **JDBC**, and **MySQL**.  
This project demonstrates clean architecture, transaction management, and database integrity.

## 🚀 Features
- Add / View / Delete Products
- Add stock to existing products
- Create invoices with multiple items
- Automatic stock reduction on invoice
- Transaction management (Commit & Rollback)
- Custom business exceptions
- MySQL foreign key integrity

## 🛠 Tech Stack
- Java (Core + JDBC)
- MySQL
- Eclipse IDE
- Git & GitHub

## 📂 Project Structure
com.inventory
├── app
├── dao
├── model
├── service
├── util
├── exception

## 🧪 How to Run
1. Create MySQL database `inventorydb`
2. Create required tables (`product`, `invoice`, `invoice_line`)
3. Update DB credentials in `DBConnection.java`
4. Run `MainApp.java`

## 📌 Key Concepts Used
- DAO Design Pattern
- Service Layer Architecture
- JDBC PreparedStatement
- Transaction Management
- Exception Handling
- Foreign Key Constraints

## 👨‍💻 Author
Aadarsh Kumar
