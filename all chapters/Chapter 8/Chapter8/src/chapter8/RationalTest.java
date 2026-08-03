
package chapter8;

public class RationalTest {
    public static void main(String[] args) {
        // Instantiate Rational numbers (testing reduced form reduction)
        Rational r1 = new Rational(2, 4);   // Auto-reduces to 1/2
        Rational r2 = new Rational(3, 9);   // Auto-reduces to 1/3
        Rational r3 = new Rational();       // Defaults to 0/1

        System.out.println("=== Reduced Fraction Initializations ===");
        System.out.println("r1 (2/4) = " + r1);
        System.out.println("r2 (3/9) = " + r2);
        System.out.println("r3 (default) = " + r3);

        System.out.println("\n=== Arithmetic Operations ===");
        
        // Addition: 1/2 + 1/3 = 5/6
        Rational sum = Rational.add(r1, r2);
        System.out.println("Addition (r1 + r2): " + sum);

        // Subtraction: 1/2 - 1/3 = 1/6
        Rational diff = Rational.subtract(r1, r2);
        System.out.println("Subtraction (r1 - r2): " + diff);

        // Multiplication: 1/2 * 1/3 = 1/6
        Rational prod = Rational.multiply(r1, r2);
        System.out.println("Multiplication (r1 * r2): " + prod);

        // Division: (1/2) / (1/3) = 3/2
        Rational div = Rational.divide(r1, r2);
        System.out.println("Division (r1 / r2): " + div);

        System.out.println("\n=== Floating-Point Output Precision ===");
        System.out.println("r1 floating point (2 digits): " + r1.toFloatString(2));
        System.out.println("r2 floating point (4 digits): " + r2.toFloatString(4));
        System.out.println("Sum floating point (6 digits): " + sum.toFloatString(6));
    }
}