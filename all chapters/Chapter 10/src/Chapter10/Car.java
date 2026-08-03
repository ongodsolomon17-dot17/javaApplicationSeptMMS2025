package chapter10;

public class Car implements CarbonFootprint { // [cite: 44, 46]
    private double gallonsUsed;

    public Car(double gallonsUsed) {
        this.gallonsUsed = gallonsUsed;
    }

    @Override
    public double getCarbonFootprint() {
        return gallonsUsed * 19.6; // Estimates kg of CO2 per gallon 
    }

    @Override
    public String toString() {
        return String.format("Car consuming %.2f gallons of fuel annually", gallonsUsed); // [cite: 45, 48]
    }
}