
package chapter8;

public enum TrafficLight {
    // Enum constants with their respective duration parameters (in seconds)
    RED(50),
    GREEN(40),
    YELLOW(5);

    // Instance variable to store the duration of each light constant
    private final int duration;

    // Enum constructor (always implicitly private)
    TrafficLight(int duration) {
        this.duration = duration;
    }

    // Getter method to retrieve the duration
    public int getDuration() {
        return duration;
    }
}
