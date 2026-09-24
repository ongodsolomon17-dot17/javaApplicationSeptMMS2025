package hospital.models;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private int id;
    private String roomNumber;
    private Ward ward;
    private String roomType;
    private int capacity;

    private List<Bed> beds = new ArrayList<>();

    public Room() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBeds(List<Bed> beds) {
        this.beds = beds;
    }

    public int getId() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Bed> getBeds() {
        return beds;
    }

    public void addBed(Bed bed) {
        beds.add(bed);
        bed.setRoom(this);
    }

    public void removeBed(Bed bed) {
        beds.remove(bed);
    }

}
