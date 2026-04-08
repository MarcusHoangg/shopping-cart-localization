package com.example.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LocalizationService {

    private DatabaseConnection databaseConnection;

    public LocalizationService(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Map<String, String> getMessages(String language) {
        Map<String, String> messages = new HashMap<>();

        String sql = "SELECT `key`, value FROM localization_strings WHERE language = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, language);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    messages.put(rs.getString("key"), rs.getString("value"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }
}