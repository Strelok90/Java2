package ru.geekbrains.java2.chat.client.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.geekbrains.java2.chat.client.ClientChat;
import ru.geekbrains.java2.chat.client.models.Network;

import java.io.IOException;

public class ViewController {

    @FXML
    public ListView<String> usersList;

    @FXML
    private Button sendButton;
    @FXML
    private TextArea chatHistory;
    @FXML
    private TextField textField;
    private Network network;
    private Stage primaryStage;

    @FXML
    public void initialize() {
        usersList.setItems(FXCollections.observableArrayList(ClientChat.USERS_TEST_DATA));
        textField.requestFocus();
    }

    @FXML
    private void sendMessage() {
        String message = textField.getText();
        appendMessage("Я: " + message);
        textField.clear();

        try {
            network.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            String errorMessage = "Failed to send message";
            ClientChat.showNetworkError(e.getMessage(), errorMessage, primaryStage);
        }
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void appendMessage(String message) {
        chatHistory.appendText(message);
        chatHistory.appendText(System.lineSeparator());
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public TextField getTextField() {
        return textField;
    }
}