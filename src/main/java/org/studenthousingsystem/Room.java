package org.studenthousingsystem;

public class Room {

    private String id, roomNumber, floor, building;
    private boolean occupied = false;
    static private int next_id = 1;

    public Room(String roomNumber, String floor, String building, boolean occupied) {
        this.id = String.valueOf(next_id);
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.building = building;
        this.occupied = occupied;
        next_id++;
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
