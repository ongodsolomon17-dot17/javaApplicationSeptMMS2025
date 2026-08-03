
package chapter8;

    public class Complex {
    // Private floating-point variables for real and imaginary parts
    private double realPart;
    private double imaginaryPart;

    // No-argument constructor initializing to default values (0.0, 0.0)
    public Complex() {
        this(0.0, 0.0);
    }

    // Constructor initializing object with specified real and imaginary parts
    public Complex(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    // a) Add two Complex numbers: (a + bi) + (c + di) = (a + c) + (b + d)i
    public Complex add(Complex rightOperand) {
        double newReal = this.realPart + rightOperand.realPart;
        double newImaginary = this.imaginaryPart + rightOperand.imaginaryPart;
        return new Complex(newReal, newImaginary);
    }

    // b) Subtract two Complex numbers: (a + bi) - (c + di) = (a - c) + (b - d)i
    public Complex subtract(Complex rightOperand) {
        double newReal = this.realPart - rightOperand.realPart;
        double newImaginary = this.imaginaryPart - rightOperand.imaginaryPart;
        return new Complex(newReal, newImaginary);
    }

    // c) Print Complex numbers in the form (realPart, imaginaryPart)
    public String toString() {
        return String.format("(%.2f, %.2f)", realPart, imaginaryPart);
    }

    // Getters
    public double getRealPart() {
        return realPart;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }
}
    

