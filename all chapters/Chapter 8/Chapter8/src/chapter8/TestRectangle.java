
package chapter8;

public class TestRectangle {
    public static void main(String[] args) {

        Rectangle rect1 = new Rectangle();

        System.out.println("Default Rectangle");
        System.out.println("Length: " + rect1.getLength());
        System.out.println("Width: " + rect1.getWidth());
        System.out.println("Perimeter: " + rect1.perimeter());
        System.out.println("Area: " + rect1.area());

        Rectangle rect2 = new Rectangle(10.5, 5.5);

        System.out.println("\nCustom Rectangle");
        System.out.println("Length: " + rect2.getLength());
        System.out.println("Width: " + rect2.getWidth());
        System.out.println("Perimeter: " + rect2.perimeter());
        System.out.println("Area: " + rect2.area());

        rect2.setLength(25.0);
        rect2.setWidth(-3.0);
    }
}