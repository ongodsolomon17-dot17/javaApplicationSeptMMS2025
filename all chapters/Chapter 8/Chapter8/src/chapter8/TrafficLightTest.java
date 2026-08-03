
package chapter8;

public class TrafficLightTest {
    public static void main(String[] args) {
        System.out.println("Traffic Light Durations:\n");
        System.out.printf("%-15s %-10s%n", "Light State", "Duration (sec)");
        System.out.println("----------------------------");

        // Iterate through all enum constants using TrafficLight.values()
        for (TrafficLight light : TrafficLight.values()) {
            System.out.printf("%-15s %-10d%n", light, light.getDuration());
        }
    }
}