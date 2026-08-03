
package classwork2;

public class TestingVehicles {
    public static void main(String[] args){
    Vehicle[] vehicleObject = {
        new Car(),
        new Bus(),
        new Bike(),
    };
    for(Vehicle vehicle : vehicleObject){
        vehicle.move();
    }
}
    
}
