package com.example.cart;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {

    @Test
    void testGetPrice() {
        Item item = new Item(10.0, 2);
        assertEquals(10.0, item.getPrice(), 0.0001);
    }

    @Test
    void testGetQuantity() {
        Item item = new Item(10.0, 2);
        assertEquals(2, item.getQuantity());
    }

    @Test
    void testGetTotalCost() {
        Item item = new Item(10.0, 2);
        assertEquals(20.0, item.getTotalCost(), 0.0001);
    }
}