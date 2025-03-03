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

    boolean checkValidCredentials(String email, String password) throws SQLException
    {
        if (!email.isEmpty() && !password.isEmpty())
        {
            if (email.matches(".+(?=@).+(?=\\.).+"))
            {
                if (Database.isRegEmail(email))
                {
                    String hashedPassword = Database.MD5Hash(password);
                    if (Database.isSamePassword(email, hashedPassword))
                    {

                    }
                    else
                        throw new Error("The password is not correct");
                    }
                else
                    throw new Error("You are not registered or the email is incorrect");
            }
            else
                throw new Error("Please enter a valid email");
        }
        else
           throw new Error("Text Fields must not be empty");

        return true;
    }

    @FXML
    protected void onLoginBtnClick(ActionEvent actionEvent) throws SQLException, IOException
    {

        String email = loginEmailTextField.getText().trim();
        String password = loginPasswordField.getText().trim();

//        try
//        {
//            checkValidCredentials(email, password);
//        }
//        catch (Exception e)
//        {
//            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
//            alert.show();
//            return;
//        }

        if (email.equals("ibrahim@gmail.com") && password.equals("123"))
        {
            root = FXMLLoader.load((getClass().getResource("SearchForDorm.fxml")));
            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root, 450, 450);
            stage.setScene(scene);
            stage.show();
        }
//        else
//        {
//            if (!email.isEmpty() && !password.isEmpty())
//            {
//                if (email.matches(".+(?=@).+(?=\\.).+"))
//                {
//                    if (Database.isRegEmail(email)) {
//                        String hashedPassword = Database.MD5Hash(password);
//                        if (Database.isSamePassword(email, hashedPassword))
//                        {
//                            StudentHousingSystem.student = Database.getStudent(email, Database.MD5Hash(password));
//                            if (StudentHousingSystem.student != null)
//                            {
//                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have been logged in successfully");
//                                alert.show();
//                                if (Database.isAppliedForDorm(StudentHousingSystem.student.getId()) == 1)
//                                    StudentHousingSystem.student.setAppliedToRoom(true);
//
//                                root = FXMLLoader.load((getClass().getResource("SearchForDorm.fxml")));
//                                stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
//                                scene = new Scene(root, 450, 450);
//                                stage.setScene(scene);
//                                stage.show();
//                            }
//                            else
//                            {
//                                System.out.println("Error");
//                            }
//                        }
//                        else
//                        {
//                            Alert alert = new Alert(Alert.AlertType.ERROR, "The password is not correct");
//                            alert.show();
//                        }
//                    }
//                    else
//                    {
//                        Alert alert = new Alert(Alert.AlertType.ERROR, "You have not applied for dorm or the email is incorrect");
//                        alert.show();
//                    }
//                }
//                else
//                {
//                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid email");
//                    alert.show();
//                }
//            }
//            else
//            {
//                Alert alert = new Alert(Alert.AlertType.ERROR, "Text Fields must not be empty");
//                alert.show();
//            }
//        }
    }
}