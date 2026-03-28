package com.example.cart;

import java.util.List;

public class TestSaveCart {
    public static void main(String[] args) {
        CartService cartService = new CartService();

        List<Item> items = List.of(
                new Item(50, 1),
                new Item(20, 5)
        );

        double total = 0;
        for (Item item : items) {
            total += item.getTotalCost();
        }

        System.out.println("About to save cart...");
        System.out.println("Total items = " + items.size());
        System.out.println("Total cost = " + total);

        cartService.saveCart(items.size(), total, "en_US", items);

        System.out.println("Finished saveCart()");
    }
}   