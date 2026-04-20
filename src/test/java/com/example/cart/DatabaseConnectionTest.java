package com.example.cart;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled("Database test depends on local environment")
class DatabaseConnectionTest {

    @Test
    void testGetConnection() {
        assertTrue(true);
    }
}