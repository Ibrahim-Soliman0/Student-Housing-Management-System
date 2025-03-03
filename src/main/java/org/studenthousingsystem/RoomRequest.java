package org.studenthousingsystem;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class RoomRequest {
    private String room;
    private Button actionButton;

    public RoomRequest(String room) {
        this.room = room;
        this.actionButton = new Button("Apply");
        if (!StudentHousingSystem.student.isAppliedToRoom())
        {
            this.actionButton.setOnAction(event -> {

                System.out.println("Applied for: " + room);

                Database.setAppliedForDorm(StudentHousingSystem.student.getId(), true);
                StudentHousingSystem.student.setAppliedToRoom(true);
            });
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You are already applied for a dorm");
            alert.show();
        }
    }

    public String getRoom() {
        return room;
    }

    public Button getActionButton() {
        return actionButton;
    }
}
