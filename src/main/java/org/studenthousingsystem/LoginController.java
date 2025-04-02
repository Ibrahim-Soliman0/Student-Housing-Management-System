package org.studenthousingsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField loginEmailTextField;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    String checkLoginCredentials(String email, String password) throws SQLException
    {
        if (!email.isEmpty() && !password.isEmpty())
        {
            if (email.matches(".+(?=@).+(?=\\.).+"))
            {
                if (Database.isRegEmail(email))
                {
                    String hashedPassword = Database.MD5Hash(password);
                    if (Database.isSamePassword(email, hashedPassword))
                        return "";
                    else
                        return "The password is not correct";
                }
                else
                    return "You are not registered or the email is incorrect";
            }
            else
                return "Please enter a valid email";
        }
        else
            return "Text Fields must not be empty";
    }

    String findPersonType(String email)
    {
        if (!Database.isStudent(email).equals("0"))
            return "Student";
        else if (!Database.isStaff(email).equals("0"))
            return "Staff";
        else
            return "Gatekeeper";
    }

    @FXML
    protected void onLoginBtnClick(ActionEvent actionEvent) throws SQLException, IOException
    {

        String email = loginEmailTextField.getText().trim();
        String password = loginPasswordField.getText().trim();

        String isValid = checkLoginCredentials(email, password);
        if (!isValid.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, isValid);
            alert.show();
            return;
        }

        String type = findPersonType(email);

        switch (type)
        {
            case "Student":
                try {
                    StudentHousingSystem.student = Database.getStudent(Database.isStudent(email));
                }
                catch (NullPointerException npe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                    alert.show();
                }
                root = FXMLLoader.load((getClass().getResource("SearchForDorm.fxml")));
                break;
            case "Staff":
                try {
                    StudentHousingSystem.staff = Database.getStaff(Database.isStaff(email));
                }
                catch (NullPointerException npe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                    alert.show();
                }
                root = FXMLLoader.load((getClass().getResource("StaffPage.fxml")));
                break;
            case "Gatekeeper":
                try {
                    StudentHousingSystem.gatekeeper = Database.getGatekeeper(Database.isGatekeeper(email));
                }
                catch (NullPointerException npe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                    alert.show();
                }
                root = FXMLLoader.load((getClass().getResource("ScannerPage.fxml")));
                break;
        }

        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 450, 450);
        stage.setScene(scene);
        stage.show();
    }
}