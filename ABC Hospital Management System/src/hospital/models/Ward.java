
package hospital.models;

import java.util.ArrayList;
import java.util.List;

public class Ward {
    private int id;
    private String name;
    private String wardType;
    private int capacity;
            
    private List<Room> rooms = new ArrayList<>();
    
    public Ward(){
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWardType() {
        return wardType;
    }

    public void setWardType(String wardType) {
        this.wardType = wardType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public List<Room> getRooms(){
        return rooms;
    }
    
     public void addRoom(Room room){
         rooms.add(room);
         room.setWard(this);
    }
     
    public void removeRoom(Room room){
        rooms.remove(room);
    }
    
}
