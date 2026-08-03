import java.util.Scanner;

public class TotalSales {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        double[][] sales = new double[5][4];

        while (true) {

            System.out.print("Salesperson (1-4,0 to stop): ");
            int person = input.nextInt();

            if (person == 0)
                break;

            System.out.print("Product (1-5): ");
            int product = input.nextInt();

            System.out.print("Amount: ");
            double amount = input.nextDouble();

            sales[product - 1][person - 1] += amount;
        }

        System.out.print("\t");

        for (int i = 1; i <= 4; i++)
            System.out.print("S" + i + "\t");

        System.out.println("Total");

        for (int row = 0; row < 5; row++) {

            double total = 0;

            System.out.print("P" + (row + 1) + "\t");

            for (int col = 0; col < 4; col++) {

                System.out.print(sales[row][col] + "\t");

                total += sales[row][col];
            }

            System.out.println(total);
        }

        input.close();
    }
}