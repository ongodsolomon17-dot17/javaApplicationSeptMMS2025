import java.util.Scanner;

public class CircleArea {

    public static double circleArea(double radius) {
        return 3.141592653589793 * radius * radius;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter radius: ");
        double radius = input.nextDouble();

        double area = circleArea(radius);

        System.out.printf("Area of circle: %.2f%n", area);

        input.close();
    }
}