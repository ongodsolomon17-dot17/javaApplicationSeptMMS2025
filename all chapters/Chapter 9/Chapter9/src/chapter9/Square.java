package chapter9;

public class Square extends Rectangle {

    private double side;

    public Square(Point p1, Point p2, Point p3, Point p4,
                  double side) {

        super(p1, p2, p3, p4, side, side);
        this.side = side;
    }

    @Override
    public double area() {
        return side * side;
    }
}


