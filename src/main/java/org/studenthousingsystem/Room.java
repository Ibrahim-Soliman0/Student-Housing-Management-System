package org.studenthousingsystem;

public class Room {

    private String id, roomNumber, floor, building;
    private boolean occupied = false;

    public Room(String roomNumber, String floor, String building, boolean occupied, String id) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.building = building;
        this.occupied = occupied;
    }

    public Room(String roomNumber, String floor, String building, boolean occupied) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.building = building;
        this.occupied = occupied;
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
}
