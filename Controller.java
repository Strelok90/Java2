package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class Controller {
    private final ObservableList<User> usersData = FXCollections.observableArrayList();
    String user = "Me";
    String[] nick = {"Alex","Bob",user};

    @FXML
    private TableView<User> tableUsers;

    @FXML
    public Button ButtonEnter;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    public TextField EnterText;

    @FXML
    public TextArea TextArena;

    @FXML
    public void ButtonClick() {
        Message msg = new Message();
        msg.setText(EnterText.getText());
        EnterText.clear();
        TextArena.appendText(user + ": " + msg.getText()+"\n");
    }

    @FXML
    private void initialize() {
        initData();
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        tableUsers.setItems(usersData);

    }

    private void initData() {
        for (String s : nick) {
            usersData.add(new User(s));
        }
    }
}

