
package chapter8;

public class HugeIntegerTest {
    public static void main(String[] args) {
        HugeInteger h1 = new HugeInteger("999999999999999999999999999999999999999"); // 39 digits of 9
        HugeInteger h2 = new HugeInteger("1");
        HugeInteger h3 = new HugeInteger("50000000000000000000");
        HugeInteger h4 = new HugeInteger("50000000000000000000");
        HugeInteger zero = new HugeInteger();

        System.out.println("=== Initial Numbers ===");
        System.out.println("h1:   " + h1);
        System.out.println("h2:   " + h2);
        System.out.println("h3:   " + h3);
        System.out.println("zero: " + zero);

        System.out.println("\n=== Arithmetic Operations ===");
        HugeInteger sum = h1.add(h2);
        System.out.println("h1 + h2 = " + sum);

        HugeInteger diff = h3.subtract(h2);
        System.out.println("h3 - h2 = " + diff);

        HugeInteger prod = h3.multiply(new HugeInteger("2"));
        System.out.println("h3 * 2  = " + prod);

        System.out.println("\n=== Predicates & Comparisons ===");
        System.out.printf("zero.isZero()                        : %b%n", zero.isZero());
        System.out.printf("h3.isEqualTo(h4)                      : %b%n", h3.isEqualTo(h4));
        System.out.printf("h1.isGreaterThan(h3)                  : %b%n", h1.isGreaterThan(h3));
        System.out.printf("h2.isLessThan(h3)                     : %b%n", h2.isLessThan(h3));
        System.out.printf("h3.isGreaterThanOrEqualTo(h4)        : %b%n", h3.isGreaterThanOrEqualTo(h4));
        System.out.printf("h2.isNotEqualTo(h3)                  : %b%n", h2.isNotEqualTo(h3));
    }
}

