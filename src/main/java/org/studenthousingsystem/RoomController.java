//package org.studenthousingsystem;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.stage.Stage;
//import javafx.scene.control.Alert.AlertType;
//
//import java.io.IOException;
//import java.util.Objects;
//
//
//public class RoomController {
//
//    @FXML
//    private TextField textfld_roomNumber = new TextField("Enter your room number"), textfld_floorNumber = new TextField("Enter your floor number");
//
//
//    @FXML
//    RadioButton radiobtn_isFilled;
//
//    @FXML
//    private Button addroom_btn;
//
//    @FXML
//    private Stage stage;
//
//    @FXML
//    private Scene scene;
//
//    private Alert alert;
//
//    @FXML
//    int roomNumberlength, floorNumberlength, roomNumber, floorNumber, isFilled;
//
//
//    @FXML
//    public void backToAdminPagefromRoomPage(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(("AdminPage.fxml"))));
//        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    @FXML
//    public void roomAddedAlert() {
//        roomNumber = Integer.parseInt(textfld_roomNumber.getText());
//        floorNumber = Integer.parseInt(textfld_floorNumber.getText());
//
//        roomNumberlength = (int) (Math.log10(roomNumber) + 1);
//        floorNumberlength = (int) (Math.log10(floorNumber) + 1);
//
//        if (roomNumberlength > 3) {
//            alert = new Alert(AlertType.ERROR, "Room number can't be more than 3 digits !");
//            alert.show();
//        } else if (floorNumberlength > 2) {
//            alert = new Alert(AlertType.ERROR, "Floor number can't be more than 2 digits !");
//            alert.show();
//        }
//        if (radiobtn_isFilled.isSelected()) {
//            isFilled = 1;
//        } else {
//            isFilled = 0;
//        }
//
//        Database.insertRoomData(roomNumber, floorNumber, isFilled);
//    }
//}
