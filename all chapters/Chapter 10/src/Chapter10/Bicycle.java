package chapter10;

public class Bicycle implements CarbonFootprint { // [cite: 44, 46]
    private double milesRidden;

    public Bicycle(double milesRidden) {
        this.milesRidden = milesRidden;
    }

    @Override
    public double getCarbonFootprint() {
        return 0.0; // Zero direct emissions during operation 
    }

    @Override
    public String toString() {
        return String.format("Bicycle ridden for %.2f miles", milesRidden); // [cite: 45, 48]
    }
}