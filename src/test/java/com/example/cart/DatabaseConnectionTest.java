package com.example.cart;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseConnectionTest {

    @Test
    void testGetConnection() throws Exception {
        Connection connection = DatabaseConnection.getConnection();
        assertNotNull(connection);
        connection.close();
    }
}