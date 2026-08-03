package chapter9;

public class CompositionTest {
    public static void main(String[] args) {

        BasePlusCommissionEmployee emp =
            new BasePlusCommissionEmployee(
                "John",
                "Smith",
                "111-11-1111",
                5000.00,
                0.10,
                300.00);

        System.out.println(emp);
        System.out.printf("Earnings: %.2f%n", emp.earnings());
    }
}
