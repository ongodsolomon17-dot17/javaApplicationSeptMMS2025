import java.util.Scanner;

public class Smallest {
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);

      System.out.print("How many numbers will you enter? ");
      int count = input.nextInt();

      System.out.print("Enter number 1: ");
      int smallest = input.nextInt();

      for (int i = 2; i <= count; i++) {
         System.out.print("Enter number " + i + ": ");
         int num = input.nextInt();
         if (num < smallest)
            smallest = num;
      }

      System.out.println("The smallest number is " + smallest);
   }
}
