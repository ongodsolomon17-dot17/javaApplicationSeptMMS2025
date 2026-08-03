package chapter10;

public class Building implements CarbonFootprint { // [cite: 44, 46]
    private double kwhUsage;

    public Building(double kwhUsage) {
        this.kwhUsage = kwhUsage;
    }

    @Override
    public double getCarbonFootprint() {
        return kwhUsage * 0.85; // Estimates kg of CO2 per kWh 
    }

    @Override
    public String toString() {
        return String.format("Building with annual electricity consumption of %.2f kWh", kwhUsage); // [cite: 45, 48]
    }
}