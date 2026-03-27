package com.example.cart;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ShoppingCartController {

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
    private final List<TextField> priceFields = new ArrayList<>();
    private final List<TextField> quantityFields = new ArrayList<>();

    private Locale currentLocale = new Locale("en", "US");

    @FXML
    public void initialize() {
        languageComboBox.setItems(FXCollections.observableArrayList(
                "English", "Finnish", "Swedish", "Japanese", "Arabic"
        ));
        languageComboBox.setValue("English");
    }

    @FXML
    private void confirmLanguage() {
        String selected = languageComboBox.getValue();
        switch (selected) {
            case "Finnish" -> currentLocale = new Locale("fi", "FI");
            case "Swedish" -> currentLocale = new Locale("sv", "SE");
            case "Japanese" -> currentLocale = new Locale("ja", "JP");
            case "Arabic" -> currentLocale = new Locale("ar", "AR");
            default -> currentLocale = new Locale("en", "US");
        }
        reloadUI();
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
                totalLabel.setText(getBundle().getString("error.invalid.item.count"));
                return;
            }
        } catch (NumberFormatException e) {
            totalLabel.setText(getBundle().getString("error.invalid.item.count"));
            return;
        }

        ResourceBundle bundle = getBundle();

        for (int i = 1; i <= itemCount; i++) {
            Label itemLabel = new Label(bundle.getString("item.label") + " " + i);

            TextField priceField = new TextField();
            priceField.setPromptText(bundle.getString("enter.item.price"));

            TextField quantityField = new TextField();
            quantityField.setPromptText(bundle.getString("enter.item.quantity"));

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
                    totalLabel.setText(getBundle().getString("error.invalid.input"));
                    return;
                }

                items.add(new Item(price, quantity));
            }

            double total = calculator.calculateCartTotal(items);
            NumberFormat format = NumberFormat.getNumberInstance(currentLocale);
            format.setMinimumFractionDigits(2);
            format.setMaximumFractionDigits(2);

            totalLabel.setText(getBundle().getString("cart.total") + " " + format.format(total));
        } catch (NumberFormatException e) {
            totalLabel.setText(getBundle().getString("error.invalid.input"));
        }
    }

    private ResourceBundle getBundle() {
        return ResourceBundle.getBundle("MessagesBundle", currentLocale);
    }

    private void reloadUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/shopping-cart.fxml"), getBundle());
            Parent root = loader.load();

            Stage stage = (Stage) languageComboBox.getScene().getWindow();
            stage.setTitle("Minh Hoang / Shopping Cart App");
            stage.setScene(new Scene(root, 650, 500));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}