package com.example.cart;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartCalculatorTest {

    private final ShoppingCartCalculator calculator = new ShoppingCartCalculator();

    @Test
    void testCalculateItemTotal() {
        double result = calculator.calculateItemTotal(10.0, 3);
        assertEquals(30.0, result, 0.0001);
    }

    @Test
    void testCalculateCartTotal() {
        List<Item> items = List.of(
                new Item(10.0, 2),   // 20
                new Item(5.5, 4),    // 22
                new Item(3.0, 1)     // 3
        );

        double result = calculator.calculateCartTotal(items);
        assertEquals(45.0, result, 0.0001);
    }

    @Test
    void testCalculateCartTotalEmptyList() {
        double result = calculator.calculateCartTotal(List.of());
        assertEquals(0.0, result, 0.0001);
    }
}