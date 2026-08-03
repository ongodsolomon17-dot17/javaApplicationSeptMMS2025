
package chapter8;


public class ComplexText {
    public static void main(String[] args) {
        // Instantiate complex numbers
        Complex c1 = new Complex(9.5, 7.7);
        Complex c2 = new Complex(1.2, 3.1);
        Complex c3 = new Complex(); // Default constructor (0.0, 0.0)

        System.out.println("--- Initial Complex Numbers ---");
        System.out.printf("c1 = %s%n", c1);
        System.out.printf("c2 = %s%n", c2);
        System.out.printf("c3 (default) = %s%n%n", c3);

        // Perform Addition: c1 + c2
        Complex sum = c1.add(c2);
        System.out.println("--- Operations ---");
        System.out.printf("c1 + c2 = %s%n", sum);

        // Perform Subtraction: c1 - c2
        Complex difference = c1.subtract(c2);
        System.out.printf("c1 - c2 = %s%n", difference);
    }
}
