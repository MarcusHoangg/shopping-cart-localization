package com.example.cart;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL = "jdbc:mariadb://localhost:3306/shopping_cart_db";
    private static final String USER = "cartuser";
    private static final String PASSWORD = "cart1234";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}