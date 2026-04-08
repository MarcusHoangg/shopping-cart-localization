package com.example.cart;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartController {

    private DatabaseConnection databaseConnection;
    @FXML private ComboBox<String> languageComboBox;
    @FXML private Label languageLabel;
    @FXML private Button confirmLanguageButton;

    @FXML private Label itemCountLabel;
    @FXML private TextField itemCountField;
    @FXML private Button enterItemsButton;

    @FXML private VBox itemsContainer;
    @FXML private Button calculateButton;
    @FXML private Label totalLabel;

    private final ShoppingCartCalculator calculator = new ShoppingCartCalculator();
    private final LocalizationService localizationService = new LocalizationService(databaseConnection);
    private final CartService cartService = new CartService();

    private final List<TextField> priceFields = new ArrayList<>();
    private final List<TextField> quantityFields = new ArrayList<>();

    private String currentLanguage = "en_US";
    private Map<String, String> currentTexts = new HashMap<>();

    @FXML
    public void initialize() {
        languageComboBox.setItems(FXCollections.observableArrayList(
                "English", "Finnish", "Swedish", "Japanese"
        ));
        languageComboBox.setValue("English");
        loadLanguage("en_US");
    }

    @FXML
    private void confirmLanguage() {
        String selected = languageComboBox.getValue();

        switch (selected) {
            case "Finnish" -> loadLanguage("fi_FI");
            case "Swedish" -> loadLanguage("sv_SE");
            case "Japanese" -> loadLanguage("ja_JP");
            default -> loadLanguage("en_US");
        }
    }

    private void loadLanguage(String language) {
        currentLanguage = language;
        currentTexts = localizationService.getMessages(language);

        languageLabel.setText(currentTexts.getOrDefault("select.language", "Select language:"));
        confirmLanguageButton.setText(currentTexts.getOrDefault("confirm.language", "Confirm Language"));
        itemCountLabel.setText(currentTexts.getOrDefault("enter.item.count", "Enter number of items:"));
        itemCountField.setPromptText(currentTexts.getOrDefault("enter.item.count.prompt", "Number of items"));
        enterItemsButton.setText(currentTexts.getOrDefault("enter.items", "Enter Items"));
        calculateButton.setText(currentTexts.getOrDefault("calculate.total", "Calculate Total"));
        totalLabel.setText(currentTexts.getOrDefault("cart.total", "Total cost:"));
    }

    @FXML
    private void createItemFields() {
        itemsContainer.getChildren().clear();
        priceFields.clear();
        quantityFields.clear();

        int itemCount;
        try {
            itemCount = Integer.parseInt(itemCountField.getText().trim());
            if (itemCount <= 0) {
                totalLabel.setText(currentTexts.getOrDefault("error.invalid.item.count", "Invalid number of items."));
                return;
            }
        } catch (NumberFormatException e) {
            totalLabel.setText(currentTexts.getOrDefault("error.invalid.item.count", "Invalid number of items."));
            return;
        }

        for (int i = 1; i <= itemCount; i++) {
            Label itemLabel = new Label(currentTexts.getOrDefault("item.label", "Item") + " " + i);

            TextField priceField = new TextField();
            priceField.setPromptText(currentTexts.getOrDefault("enter.item.price", "Enter price for item:"));

            TextField quantityField = new TextField();
            quantityField.setPromptText(currentTexts.getOrDefault("enter.item.quantity", "Enter quantity for item:"));

            priceFields.add(priceField);
            quantityFields.add(quantityField);

            VBox itemBox = new VBox(5, itemLabel, priceField, quantityField);
            itemsContainer.getChildren().add(itemBox);
        }
    }

    @FXML
    private void calculateTotal() {
        List<Item> items = new ArrayList<>();

        try {
            for (int i = 0; i < priceFields.size(); i++) {
                double price = Double.parseDouble(priceFields.get(i).getText().trim());
                int quantity = Integer.parseInt(quantityFields.get(i).getText().trim());

                if (price < 0 || quantity <= 0) {
                    totalLabel.setText(currentTexts.getOrDefault("error.invalid.input", "Invalid input."));
                    return;
                }

                items.add(new Item(price, quantity));
            }

            double total = calculator.calculateCartTotal(items);
            NumberFormat format = NumberFormat.getNumberInstance();
            format.setMinimumFractionDigits(2);
            format.setMaximumFractionDigits(2);

            totalLabel.setText(currentTexts.getOrDefault("cart.total", "Total cost:") + " " + format.format(total));

            cartService.saveCart(items.size(), total, currentLanguage, items);

        } catch (NumberFormatException e) {
            totalLabel.setText(currentTexts.getOrDefault("error.invalid.input", "Invalid input."));
        }
    }
}