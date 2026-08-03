package chapter9;

public class Rectangle extends Parallelogram {

    protected double length;
    protected double width;

    public Rectangle(Point p1, Point p2, Point p3, Point p4,
                     double length, double width) {

        super(p1, p2, p3, p4, length, width);
        this.length = length;
        this.width = width;
    }

    @Override
    public double area() {
        return length * width;
    }
}
