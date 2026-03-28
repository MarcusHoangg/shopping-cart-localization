package com.example.cart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDB {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM messages")) {

            while (rs.next()) {
                System.out.println(rs.getString("message_text"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}