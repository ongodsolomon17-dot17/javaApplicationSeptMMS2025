import java.util.Scanner;

public class FairTax {
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      double total = 0;

      System.out.print("Enter housing expenses: ");
      total = total + input.nextDouble();
      System.out.print("Enter food expenses: ");
      total = total + input.nextDouble();
      System.out.print("Enter clothing expenses: ");
      total = total + input.nextDouble();
      System.out.print("Enter transportation expenses: ");
      total = total + input.nextDouble();
      System.out.print("Enter education expenses: ");
      total = total + input.nextDouble();
      System.out.print("Enter health care expenses: ");
      total = total + input.nextDouble();
      System.out.print("Enter vacation expenses: ");
      total = total + input.nextDouble();

      double fairTax = total * 0.23;
      System.out.printf("Total expenses: $%.2f%n", total);
      System.out.printf("Estimated FairTax (23%%): $%.2f%n", fairTax);
   }
}
