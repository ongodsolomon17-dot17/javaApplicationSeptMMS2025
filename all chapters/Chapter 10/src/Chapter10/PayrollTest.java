package chapter10;

import java.time.LocalDate;

public class PayrollTest {
    public static void main(String[] args) {
        // Create sample employees
        Employee[] employees = new Employee[2];

        // Current month for testing (or retrieve using java.time.LocalDate)
        int currentMonth = LocalDate.now().getMonthValue(); 

        employees[0] = new SalariedEmployee("John", "Smith", "111-11-1111", 
            new Date(currentMonth, 15, 1995), 800.00); // Birthday is THIS month
            
        employees[1] = new SalariedEmployee("Jane", "Doe", "222-22-2222", 
            new Date((currentMonth % 12) + 1, 20, 1998), 950.00); // Birthday in ANOTHER month

        System.out.println("--- Processing Payroll Polymorphically --- \n");

        for (Employee currentEmployee : employees) {
            System.out.println(currentEmployee);

            double payrollAmount = currentEmployee.earnings();

            // Check if employee's birth month matches current month
            if (currentEmployee.getBirthDate().getMonth() == currentMonth) {
                payrollAmount += 100.00; // Add $100 bonus
                System.out.println("** Happy Birthday! A $100.00 bonus has been added. **");
            }

            System.out.printf("Earned: $%,.2f%n%n", payrollAmount);
        }
    }
}