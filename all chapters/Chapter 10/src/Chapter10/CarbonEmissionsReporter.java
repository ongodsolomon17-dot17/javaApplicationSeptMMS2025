package chapter10;

import java.util.ArrayList;

public class CarbonEmissionsReporter {
    public static void main(String[] args) {
        ArrayList<CarbonFootprint> list = new ArrayList<>(); // [cite: 47]

        list.add(new Building(15000.0)); // [cite: 44, 47]
        list.add(new Car(520.0));        // [cite: 44, 47]
        list.add(new Bicycle(450.0));    // [cite: 44, 47]

        System.out.println("--- Carbon Footprint Report ---\n");

        for (CarbonFootprint item : list) { // [cite: 47]
            System.out.println(item.toString()); // [cite: 48]
            System.out.printf("Annual Carbon Footprint: %,.2f kg CO2%n%n", item.getCarbonFootprint()); // 
        }
    }
}