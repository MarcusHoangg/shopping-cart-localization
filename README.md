# Shopping Cart Application (Database Localization)

## Overview
This is a JavaFX shopping cart application.

The app allows users to enter items, calculate total cost, and save the data into a database.

It also supports multiple languages (English, Finnish, Japanese, etc.) using data stored in the database.

---
## Features
- Select language
- Enter number of items
- Input price and quantity
- Calculate total cost
- Save cart data to database
- Multi-language UI

---

## Technologies
- Java 17
- JavaFX
- Maven
- MySQL / MariaDB
- JDBC

---

## How to Run

### 1. Setup Database
Run the `database.sql` file to create database and tables.

### 2. Run Application
```bash
mvn clean javafx:run

###
```
Database Tables
cart_records: stores total items, total cost, language, timestamp
cart_items: stores each item in the cart
localization_strings: stores UI text for different languages
```