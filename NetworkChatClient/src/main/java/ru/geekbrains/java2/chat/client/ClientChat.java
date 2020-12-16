package ru.geekbrains.java2.chat.client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.geekbrains.java2.chat.client.controllers.AuthController;
import ru.geekbrains.java2.chat.client.controllers.ViewController;
import ru.geekbrains.java2.chat.client.models.ClientChatState;
import ru.geekbrains.java2.chat.client.models.Network;

import java.io.IOException;
import java.util.List;


public class ClientChat extends Application {

    public static final List<String> USERS_TEST_DATA = List.of("Oleg", "Alexey", "Peter");

    private ClientChatState state = ClientChatState.AUTHENTICATION;
    private Stage primaryStage;
    private Stage authDialogStage;
    private Network network;
    private ViewController viewController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientChat.class.getResource("view.fxml"));

        Parent root = loader.load();
        viewController = loader.getController();

        primaryStage.setTitle("Messenger");
        primaryStage.setScene(new Scene(root, 600, 400));
        viewController.getTextField().requestFocus();

        setStageForSecondScreen(primaryStage);

        network = new Network(this);
        if (!network.connect()) {
            showNetworkError("", "Failed to connect to server", primaryStage);
        }

        viewController.setNetwork(network);
        viewController.setStage(primaryStage);

        network.waitMessages(viewController);

        primaryStage.setOnCloseRequest(event -> {
            try {
                network.sendMessage("/end");
            } catch (IOException e) {
                e.printStackTrace();
            }
            network.close();
        });

        openAuthDialog();
    }

    private void openAuthDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientChat.class.getResource("authDialog.fxml"));
        AnchorPane parent = loader.load();

        authDialogStage = new Stage();
        authDialogStage.initModality(Modality.WINDOW_MODAL);
        authDialogStage.initOwner(primaryStage);

        AuthController authController = loader.getController();
        authController.setNetwork(network);

        authDialogStage.setScene(new Scene(parent));
        setStageForSecondScreen(authDialogStage);
        authDialogStage.show();
    }

    public static void showNetworkError(String errorDetails, String errorTitle, Stage dialogStage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (dialogStage != null) {
            alert.initOwner(dialogStage);
        }
        alert.setTitle("Network Error");
        alert.setHeaderText(errorTitle);
        alert.setContentText(errorDetails);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setStageForSecondScreen(Stage primaryStage) {
        Screen secondScreen = getSecondScreen();
        Rectangle2D bounds = secondScreen.getBounds();
        primaryStage.setX(bounds.getMinX() + (bounds.getWidth() - 300) / 2);
        primaryStage.setY(bounds.getMinY() + (bounds.getHeight() - 200) / 2);
    }

    private Screen getSecondScreen() {
        for (Screen screen : Screen.getScreens()) {
            if (!screen.equals(Screen.getPrimary())) {
                return screen;
            }
        }
        return Screen.getPrimary();
    }

    public ClientChatState getState() {
        return state;
    }

    public void setState(ClientChatState state) {
        this.state = state;
    }

    public void activeChatDialog(String nickname) {
        primaryStage.setTitle(nickname);
        state = ClientChatState.CHAT;
        authDialogStage.close();
        primaryStage.show();
        viewController.getTextField().requestFocus();
    }
}