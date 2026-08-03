package chapter9;

public class Trapezoid extends Quadrilateral {

    protected double base1;
    protected double base2;
    protected double height;

    public Trapezoid(Point p1, Point p2, Point p3, Point p4,
                     double base1, double base2, double height) {

        super(p1, p2, p3, p4);
        this.base1 = base1;
        this.base2 = base2;
        this.height = height;
    }

    public double area() {
        return ((base1 + base2) * height) / 2.0;
    }
}