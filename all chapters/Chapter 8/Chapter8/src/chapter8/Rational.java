
package chapter8;

public class Rational {
    private int numerator;
    private int denominator;

    // No-argument constructor: defaults to 0/1
    public Rational() {
        this(0, 1);
    }

    // Constructor: normalizes signs and reduces fraction
    public Rational(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }

        // Keep negative sign on numerator only
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }

        // Reduce fraction to lowest terms
        int gcd = gcd(Math.abs(numerator), denominator);
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    // Euclidean Algorithm to compute Greatest Common Divisor
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // a) Addition: (a/b) + (c/d) = (a*d + b*c) / (b*d)
    public static Rational add(Rational r1, Rational r2) {
        int num = r1.numerator * r2.denominator + r2.numerator * r1.denominator;
        int den = r1.denominator * r2.denominator;
        return new Rational(num, den);
    }

    // b) Subtraction: (a/b) - (c/d) = (a*d - b*c) / (b*d)
    public static Rational subtract(Rational r1, Rational r2) {
        int num = r1.numerator * r2.denominator - r2.numerator * r1.denominator;
        int den = r1.denominator * r2.denominator;
        return new Rational(num, den);
    }

    // c) Multiplication: (a/b) * (c/d) = (a*c) / (b*d)
    public static Rational multiply(Rational r1, Rational r2) {
        int num = r1.numerator * r2.numerator;
        int den = r1.denominator * r2.denominator;
        return new Rational(num, den);
    }

    // d) Division: (a/b) / (c/d) = (a*d) / (b*c)
    public static Rational divide(Rational r1, Rational r2) {
        if (r2.numerator == 0) {
            throw new ArithmeticException("Cannot divide by zero fraction.");
        }
        int num = r1.numerator * r2.denominator;
        int den = r1.denominator * r2.numerator;
        return new Rational(num, den);
    }

    // e) String representation in fraction form a/b
    public String toString() {
        return (denominator == 1) ? String.valueOf(numerator) : numerator + "/" + denominator;
    }

    // f) String representation in floating-point format with specified precision
    public String toFloatString(int digitsOfPrecision) {
        if (digitsOfPrecision < 0) {
            throw new IllegalArgumentException("Precision must be non-negative.");
        }
        double value = (double) numerator / denominator;
        return String.format("%." + digitsOfPrecision + "f", value);
    }
}
