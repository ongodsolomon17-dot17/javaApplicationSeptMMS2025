package chapter9;

public class HourlyEmployeeTest {

    public static void main(String[] args) {

        HourlyEmployee employee =
                new HourlyEmployee(
                "John",
                "Smith",
                "111-22-3333",
                20.00,
                45);


        System.out.println(employee);

        System.out.printf(
                "Earnings: %.2f%n",
                employee.earnings());
    }
}
