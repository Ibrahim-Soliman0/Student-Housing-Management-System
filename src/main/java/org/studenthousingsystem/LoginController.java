//package org.studenthousingsystem;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//public class LoginController {
//    @FXML
//    private TextField loginEmailTextField;
//    @FXML
//    private PasswordField loginPasswordField;
//    @FXML
//    private Stage stage;
//    @FXML
//    private Scene scene;
//    @FXML
//    private Parent root;
//    private String email = "ibrahim@gmail.com", password = "123";
//
//    @FXML
//    protected void onLoginBtnClick(ActionEvent actionEvent) throws SQLException, IOException {
//        String Email = loginEmailTextField.getText().trim();
//        String Password = loginPasswordField.getText().trim();
//        if (email.equals("ibrahim@gmail.com") && Password.equals("123"))
//        {
//            root = FXMLLoader.load((getClass().getResource("SearchForDorm.fxml")));
//            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
//            scene = new Scene(root, 450, 450);
//            stage.setScene(scene);
//            stage.show();
//        }
//        else
//        {
//            if (!Email.isEmpty() && !Password.isEmpty()) {
//                if (Email.matches(".+(?=@).+(?=\\.).+")){
//                    if (Database.isRegEmail(Email)) {
//                        String hashedPassword = Database.MD5Hash(Password);
//                        if (Database.isSamePassword(Email, hashedPassword)){
//                            StudentHousingSystem.student = Database.getStudent(Email, Database.MD5Hash(Password));
//                            if (StudentHousingSystem.student != null) {
//                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have been logged in successfully");
//                                alert.show();
//                                if (Database.isAppliedForDorm(StudentHousingSystem.student.getId()) == 1)
//                                    StudentHousingSystem.student.setApplied(1);
//
//                                root = FXMLLoader.load((getClass().getResource("SearchForDorm.fxml")));
//                                stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
//                                scene = new Scene(root, 450, 450);
//                                stage.setScene(scene);
//                                stage.show();
//                            } else {
//                                System.out.println("Error");
//                            }
//                        }
//                        else {
//                            Alert alert = new Alert(Alert.AlertType.ERROR, "The password is not correct");
//                            alert.show();
//                        }
//                    }
//                    else {
//                        Alert alert = new Alert(Alert.AlertType.ERROR, "You have not applied for dorm or the email is incorrect");
//                        alert.show();
//                    }
//                }
//                else {
//                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid email");
//                    alert.show();
//                }
//            }
//            else {
//                Alert alert = new Alert(Alert.AlertType.ERROR, "Text Fields must not be empty");
//                alert.show();
//            }
//        }
//    }
//}