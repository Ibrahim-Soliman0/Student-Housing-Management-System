package org.studenthousingsystem;

import javafx.scene.control.Button;

public class PendingRequest {
    private String roomID;
    private String studentID;
    private Button acceptBtn;
    private Button declineBtn;

    public PendingRequest(String roomID, String studentID) {
        this.roomID = roomID;
        this.studentID = studentID;
        this.acceptBtn = new Button("Accept");
        this.acceptBtn.setOnAction(event -> {
            System.out.println("Request Accepted");
        });
        this.declineBtn = new Button("Decline");
        this.declineBtn.setOnAction(event -> {
            System.out.println("Request Declined");
        });
    }

    public String getRoomID() {
        return roomID;
    }

    public String getStudentID() {
        return studentID;
    }
}
