import java.util.Scanner;

public class SalesCommissions {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Array to count salaries in each range
        int[] salaryRanges = new int[9];

        System.out.print("Enter number of salespeople: ");
        int numberOfSalespeople = input.nextInt();

        for (int i = 1; i <= numberOfSalespeople; i++) {

            System.out.print("Enter gross sales for salesperson " + i + ": ");
            double sales = input.nextDouble();

            // Calculate salary
            int salary = (int) (200 + 0.09 * sales);

            // Determine salary range
            if (salary >= 1000)
                salaryRanges[8]++;
            else
                salaryRanges[(salary - 200) / 100]++;
        }

        // Display results
        System.out.println("\nSalary Range\tNumber of Salespeople");
        System.out.println("$200 - $299\t" + salaryRanges[0]);
        System.out.println("$300 - $399\t" + salaryRanges[1]);
        System.out.println("$400 - $499\t" + salaryRanges[2]);
        System.out.println("$500 - $599\t" + salaryRanges[3]);
        System.out.println("$600 - $699\t" + salaryRanges[4]);
        System.out.println("$700 - $799\t" + salaryRanges[5]);
        System.out.println("$800 - $899\t" + salaryRanges[6]);
        System.out.println("$900 - $999\t" + salaryRanges[7]);
        System.out.println("$1000 and over\t" + salaryRanges[8]);

        input.close();
    }
}