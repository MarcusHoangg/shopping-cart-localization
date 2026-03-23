package com.example.cart;

import java.util.List;

public class ShoppingCartCalculator {

    public double calculateItemTotal(double price, int quantity) {
        return price * quantity;
    }

    public double calculateCartTotal(List<Item> items) {
        double total = 0.0;
        for (Item item : items) {
            total += item.getTotalCost();
        }
        return total;
    }
}