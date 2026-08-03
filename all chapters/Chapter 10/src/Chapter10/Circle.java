package chapter10;

public class Circle extends TwoDimensionalShape {
    private double radius;

    public Circle(double radius) {
        if (radius <= 0.0) throw new IllegalArgumentException("Radius must be > 0.0");
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public String toString() {
        return String.format("Circle with radius %.2f", radius);
    }
}