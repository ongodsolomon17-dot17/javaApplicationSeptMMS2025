package chapter9;

public class Parallelogram extends Trapezoid {

    protected double base;

    public Parallelogram(Point p1, Point p2, Point p3, Point p4,
                         double base, double height) {

        super(p1, p2, p3, p4, base, base, height);
        this.base = base;
    }

    @Override
    public double area() {
        return base * height;
    }
}
