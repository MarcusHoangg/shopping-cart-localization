package com.example.cart;

import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.setOut(new java.io.PrintStream(System.out, true, StandardCharsets.UTF_8));

        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        Locale locale = selectLocale(scanner);
        ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", locale);

        ShoppingCartCalculator calculator = new ShoppingCartCalculator();
        List<Item> items = new ArrayList<>();

        System.out.println(messages.getString("welcome"));

        int itemCount = readPositiveInt(scanner, messages.getString("enter.item.count"));

        for (int i = 1; i <= itemCount; i++) {
            System.out.println(messages.getString("item.number") + " " + i);

            double price = readNonNegativeDouble(scanner, messages.getString("enter.item.price"));
            int quantity = readPositiveInt(scanner, messages.getString("enter.item.quantity"));

            Item item = new Item(price, quantity);
            items.add(item);

            double itemTotal = calculator.calculateItemTotal(price, quantity);
            System.out.println(messages.getString("item.total") + " " +
                    formatCurrency(itemTotal, locale));
        }

        double cartTotal = calculator.calculateCartTotal(items);
        System.out.println(messages.getString("cart.total") + " " +
                formatCurrency(cartTotal, locale));

        scanner.close();
    }

    private static Locale selectLocale(Scanner scanner) {
        System.out.println("Select language / Valitse kieli / Välj språk / 言語を選択してください:");
        System.out.println("1. English");
        System.out.println("2. Finnish");
        System.out.println("3. Swedish");
        System.out.println("4. Japanese");
        System.out.print("Choice: ");

        String choice = scanner.nextLine().trim();

        return switch (choice) {
            case "2" -> new Locale("fi", "FI");
            case "3" -> new Locale("sv", "SE");
            case "4" -> new Locale("ja", "JP");
            default -> new Locale("en", "US");
        };
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " ");
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a positive integer.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static double readNonNegativeDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " ");
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value >= 0) {
                    return value;
                }
                System.out.println("Please enter a non-negative number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static String formatCurrency(double amount, Locale locale) {
        NumberFormat format = NumberFormat.getNumberInstance(locale);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        return format.format(amount);
    }
}