package org.studenthousingsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;





public class AddStudentController implements Initializable {

    @FXML
    private TextField email_textfield , id_textfield , name_textfield;
    @FXML
    PasswordField password_passwordField ;
    @FXML
    Button addButton, showAdminpage_btn;
    @FXML
    Label myLabel;
    @FXML
    private ChoiceBox<String> myChoiceBox;

    private String[] Cities = {"Alexandria", "Aswan", "Assiout", "Beheira", "Beni Souef", "Cairo", "Dakahleya", "Damietta",
    "Fayoum", "Gharbeya", "Giza", "Ismailia", "Kafr el-Cheik", "Marsa-Matruh", "Minya", "Menufeya", "New Valley",
    "North Sinai", "Port Said", "Qalyubiya", "Qena", "Red Sea", "Ach-Charqiya", "Sohag", "South Sinai", "Suez", "Luxor"};

    private Stage stage;
    @FXML
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myChoiceBox.getItems().addAll(Cities);
    }

    @FXML
    public void showAdminPage(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(("StaffPage.fxml")));
        stage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addButtonAction() throws SQLException {
        email_textfield.setPromptText("Enter your email");

        String email, id, name, city, password;
        email = email_textfield.getText().trim();
        id = id_textfield.getText().trim();
        name = name_textfield.getText().trim();
        city = myChoiceBox.getValue();
        password = password_passwordField.getText().trim();

        Alert alert;

        if (password.length() < 8) {
            alert = new Alert(AlertType.ERROR, "Password must be at least 8 characters long.");
            alert.show();
        }
        else if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            alert = new Alert(AlertType.ERROR, "Invalid email format.");
            alert.show();
        }
        else if (id.isEmpty()) {
            alert = new Alert(AlertType.ERROR, "ID cannot be empty.");
            alert.show();
        }
        else if (name.isEmpty()) {
            alert = new Alert(AlertType.ERROR, "Name cannot be empty.");
            alert.show();
        }
        else if (city == null) {
            alert = new Alert(AlertType.ERROR, "Please choose a city");
            alert.show();
        }
        else {
            Database.insertStudent(
                    new Student(id, name, email, city,
                    Database.MD5Hash(password), 0, false, false));
        }
    }
}

