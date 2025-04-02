package org.studenthousingsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class RoomController {

    @FXML
    private TextField roomNumberTextField = new TextField("Enter your room number"),
            floorNumberTextField = new TextField("Enter your floor number"),
            buildingNumberTextField = new TextField("Enter your building number");

    @FXML
    CheckBox isOccupied;

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    public void backToAdminPagefromRoomPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(("StaffPage.fxml"))));
        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void roomAddedAlert()
    {
        String roomNumber = roomNumberTextField.getText().trim();
        String floorNumber = floorNumberTextField.getText().trim();
        String buildingNumber = buildingNumberTextField.getText().trim();

        boolean isFilled = isOccupied.isSelected();

        Database.insertRoomData(new Room(roomNumber, floorNumber, buildingNumber, isFilled));
    }
}
