package chapter9;

public class ShapeTest {

    public static void main(String[] args) {

        Point p1 = new Point(0, 0);
        Point p2 = new Point(8, 0);
        Point p3 = new Point(6, 4);
        Point p4 = new Point(1, 4);

        Trapezoid trapezoid = new Trapezoid(p1, p2, p3, p4, 8, 5, 4);
        Parallelogram parallelogram = new Parallelogram(p1, p2, p3, p4, 6, 4);
        Rectangle rectangle = new Rectangle(p1, p2, p3, p4, 5, 4);
        Square square = new Square(p1, p2, p3, p4, 4);

        System.out.println("Trapezoid Area = " + trapezoid.area());
        System.out.println("Parallelogram Area = " + parallelogram.area());
        System.out.println("Rectangle Area = " + rectangle.area());
        System.out.println("Square Area = " + square.area());
    }
}
