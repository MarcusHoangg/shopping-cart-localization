package com.example.cart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        System.setProperty("file.encoding", StandardCharsets.UTF_8.name());

        Locale locale = new Locale("en", "US");
        ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", locale);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/shopping-cart.fxml"), bundle);
        Scene scene = new Scene(loader.load(), 650, 500);

        stage.setTitle("Minh Hoang / Shopping Cart App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}