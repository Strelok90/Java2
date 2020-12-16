package ru.geekbrains.java2.chat.client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;


public class Main extends Application {

    public static final List<String> USERS_TEST_DATA = List.of("Oleg", "Alexey", "Peter");

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view.fxml"));

        AnchorPane root = loader.load();

        setStageForSecondScreen(primaryStage);

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
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

    public static void main(String[] args) {
        launch(args);
    }
}