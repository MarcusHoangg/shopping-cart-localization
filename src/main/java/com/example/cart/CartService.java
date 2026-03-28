package com.example.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CartService {

    public void saveCart(int totalItems, double totalCost, String language, List<Item> items) {
        String insertCart = "INSERT INTO cart_records (total_items, total_cost, language) VALUES (?, ?, ?)";
        String insertItem = "INSERT INTO cart_items (cart_record_id, item_number, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            int cartRecordId;

            try (PreparedStatement cartStmt = conn.prepareStatement(insertCart, PreparedStatement.RETURN_GENERATED_KEYS)) {
                cartStmt.setInt(1, totalItems);
                cartStmt.setDouble(2, totalCost);
                cartStmt.setString(3, language);
                cartStmt.executeUpdate();

                try (ResultSet rs = cartStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cartRecordId = rs.getInt(1);
                    } else {
                        throw new RuntimeException("Could not get cart record ID.");
                    }
                }
            }

            try (PreparedStatement itemStmt = conn.prepareStatement(insertItem)) {
                for (int i = 0; i < items.size(); i++) {
                    Item item = items.get(i);
                    itemStmt.setInt(1, cartRecordId);
                    itemStmt.setInt(2, i + 1);
                    itemStmt.setDouble(3, item.getPrice());
                    itemStmt.setInt(4, item.getQuantity());
                    itemStmt.setDouble(5, item.getTotalCost());
                    itemStmt.addBatch();
                }
                itemStmt.executeBatch();
            }

            conn.commit();
            System.out.println("Cart saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}