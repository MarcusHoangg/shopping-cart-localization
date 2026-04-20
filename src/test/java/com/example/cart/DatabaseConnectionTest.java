package com.example.cart;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Database test depends on local environment")
public class DatabaseConnectionTest {

    @Test
    void testGetConnection() {
        // disabled for CI
    }
}