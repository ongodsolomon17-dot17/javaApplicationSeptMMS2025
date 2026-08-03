
package chapter8;

public class Rectangle {
    private double length;
    private double width;

    public Rectangle() {
        length = 1.0;
        width = 1.0;
    }
    public Rectangle(double length, double width) {
        setLength(length);
        setWidth(width);
    }
    public void setLength(double length) {
        if (length > 0.0 && length < 20.0) {
            this.length = length;
        } else {
            System.out.println("Invalid length! Must be greater than 0.0 and less than 20.0.");
        }
    }
    public double getLength() {
        return length;
    }
    public void setWidth(double width) {
        if (width > 0.0 && width < 20.0) {
            this.width = width;
        } else {
            System.out.println("Invalid width! Must be greater than 0.0 and less than 20.0.");
        }
    }
    public double getWidth() {
        return width;
    }
    public double perimeter() {
        return 2 * (length + width);
    }
    public double area() {
        return length * width;
    }
}
