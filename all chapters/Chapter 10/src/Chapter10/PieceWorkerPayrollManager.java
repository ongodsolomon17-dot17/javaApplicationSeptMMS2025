package chapter10;

public class PieceWorkerPayrollManager {
    public static void main(String[] args) {
        Employee[] employees = new Employee[2]; // 

        employees[0] = new SalariedEmployee("Alice", "Brown", "333-33-3333", 
            new Date(5, 10, 1990), 1200.00);
        employees[1] = new PieceWorker("Bob", "Green", "444-44-4444", 
            new Date(8, 22, 1992), 12.50, 85); // 

        System.out.println("--- PieceWorker Employee Payroll Report ---\n");

        for (Employee currentEmployee : employees) {
            System.out.println(currentEmployee); // [cite: 19]
            System.out.printf("Earnings: $%,.2f%n%n", currentEmployee.earnings()); // [cite: 19]
        }
    }
}