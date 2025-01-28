//package org.studenthousingsystem;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.stage.Stage;
//import javafx.scene.control.Alert.AlertType;
//
//import java.io.IOException;
//import java.net.URL;
//import java.sql.*;
//import java.util.ResourceBundle;
//
//
//
//
//
//public class StudentAddController implements Initializable {
//
//    @FXML
//    private TextField email_textfield , id_textfield , name_textfield;
//
//
//    @FXML
//    PasswordField password_passwordField ;
//    @FXML
//    Button addButton, showAdminpage_btn;
//    @FXML
//    Label myLabel;
//    @FXML
//    private ChoiceBox<String> myChoiceBox;
//
//    String email, id, name, city, hashedPassword , password  ;
//    Student st;
//
//    static Alert alert;
//    static PreparedStatement preparedStatement = null;
//    static ResultSet resultSet = null;
//
//
//    private String[] Cities = {"Cairo", "Alexandria", "Giza", "Shubra El-Kheima", "Port Said", "Suez", "Luxor", "Aswan",
//            "Tanta", "Mansoura", "Al-Minya", "Fayoum", "Damanhur", "Damietta", "Beni Suef", "Sohag",
//            "Hurghada", "Zagazig", "Asyut", "Qena", "Suez", "Ismailia", "Khusus", "Luxor"};
//
//    private Stage stage;
//    @FXML
//    private Scene scene;
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        myChoiceBox.getItems().addAll(Cities);
//    }
//
//    @FXML
//    public void showAdminPage(javafx.event.ActionEvent actionEvent) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource(("AdminPage.fxml")));
//        stage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    @FXML
//    void addButtonAction() throws SQLException {
//        email_textfield.setPromptText("enter your email");
//        email = email_textfield.getText();
//        id = id_textfield.getText();
//        name = name_textfield.getText();
//        city = myChoiceBox.getValue();
//        password = password_passwordField.getText();
//        hashedPassword = Database.MD5Hash(password);
//        st = new Student(name, email, id, city);
//
//        if (password.length() < 8) {
//            alert = new Alert(AlertType.ERROR, "Password must be at least 8 characters long.");
//            alert.show();
//        } else if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
//            alert = new Alert(AlertType.ERROR, "Invalid email format.");
//            alert.show();
//        } else if (id.isEmpty()) {
//            alert = new Alert(AlertType.ERROR, "ID cannot be empty.");
//            alert.show();
//        } else if (name.isEmpty()) {
//            alert = new Alert(AlertType.ERROR, "Name cannot be empty.");
//            alert.show();
//        } else if (city == null) {
//            alert = new Alert(AlertType.ERROR, "Please choose a city");
//            alert.show();
//        } else {
//            Database.insertStudentData(id, name, city, email, hashedPassword, 0, false, 0);
//        }
//    }
//}
//
