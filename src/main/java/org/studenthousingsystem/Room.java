package org.studenthousingsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class Room {

    private String id, roomNumber, floor, building;
    private boolean occupied = false;
    private final Button applyButton;

    public Room(String roomNumber, String floor, String building, boolean occupied, String id) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.building = building;
        this.occupied = occupied;
        this.applyButton = new Button("Apply");
        this.applyButton.setOnAction(setApplyAction(StudentHousingSystem.student));
    }

    public Room(String roomNumber, String floor, String building, boolean occupied) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.building = building;
        this.occupied = occupied;
        this.applyButton = new Button("Apply");
        this.applyButton.setOnAction(setApplyAction(StudentHousingSystem.student));
    }

    private EventHandler<ActionEvent> setApplyAction(Student student)
    {
        return event -> {
            if(student.isAppliedToRoom())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "You are already applied for a room");
                alert.setTitle("Failed to Apply");
                alert.show();
            }
            else
            {
                Database.setAppliedForDorm(student.getStudentId(), true);
                Database.makeRoomRequest(new RoomRequest(this, student, "pending"));
                student.setAppliedToRoom(true);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Applied for Number: " + this.getRoomNumber() + " at Building: " + this.getBuilding());
                alert.setTitle("Applied Successfully");
                alert.show();
            }
        };
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Button getApplyButton() {
        return applyButton;
    }
}
