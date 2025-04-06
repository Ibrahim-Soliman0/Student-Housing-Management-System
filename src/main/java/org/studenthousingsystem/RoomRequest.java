package org.studenthousingsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class RoomRequest {
    private Room room;
    private Student student;
    private Staff staff;
    private String status;
    private final Button approveButton, rejectButton;

    public RoomRequest(Room room, Student student, Staff staff, String status) {
        this.room = room;
        this.student = student;
        this.staff = staff;
        this.status = status;
        this.approveButton = new Button("✅");
        this.rejectButton = new Button("❌");
        rejectButton.setStyle("-fx-background-color: #ff4c4c; -fx-text-fill: white;");
//        this.approveButton.setOnAction(setApproveAction(StudentHousingSystem.roomRequest, StudentHousingSystem.staff));
//        this.rejectButton.setOnAction(setRejectAction(StudentHousingSystem.roomRequest, StudentHousingSystem.staff));
    }

    public RoomRequest(Room room, Student student, String status) {
        this.room = room;
        this.student = student;
        this.staff = null;
        this.status = status;
        this.approveButton = new Button("✅");
        this.rejectButton = new Button("❌");
        rejectButton.setStyle("-fx-background-color: #ff4c4c; -fx-text-fill: white;");
//        this.approveButton.setOnAction(setApproveAction(StudentHousingSystem.roomRequest, StudentHousingSystem.staff));
//        this.rejectButton.setOnAction(setRejectAction(StudentHousingSystem.roomRequest, StudentHousingSystem.staff));
    }

//    private EventHandler<ActionEvent> setApproveAction(RoomRequest roomRequest, Staff staff)
//    {
//        return event -> {
//            roomRequest.setStaff(staff);
//            Database.approveRoomRequest(roomRequest);
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The student has been Approved");
//            alert.setTitle("Applied Successfully");
//            alert.show();
//        };
//    }
//
//    private EventHandler<ActionEvent> setRejectAction(RoomRequest roomRequest, Staff staff)
//    {
//        return event -> {
//            roomRequest.setStaff(staff);
//            Database.rejectRoomRequest(roomRequest);
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The student has been Rejected");
//            alert.setTitle("Applied Successfully");
//            alert.show();
//        };
//    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Button getApproveButton() {
        return approveButton;
    }

    public Button getRejectButton() {
        return rejectButton;
    }
}
