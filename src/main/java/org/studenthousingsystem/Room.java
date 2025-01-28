package org.studenthousingsystem;

public class Room {

    static int roomNumber , floorNumber ;
    static int isFilled;


    public Room(int roomNumber, int floorNumber, int isFilled) {
        Room.roomNumber = roomNumber;
        Room.floorNumber = floorNumber;
        Room.isFilled = isFilled ;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int isFilled() {
        return isFilled;
    }

    public void setRoomNumber(int roomNumber) {
        Room.roomNumber = roomNumber;
    }

    public void setFloorNumber(int floorNumber) {
        Room.floorNumber = floorNumber;
    }

    public void setFilled(int isFilled) {Room.isFilled = isFilled;
    }
}
