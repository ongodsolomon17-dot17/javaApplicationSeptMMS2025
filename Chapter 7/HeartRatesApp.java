import java.util.Scanner;

public class HeartRatesApp {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("First name: ");
        String first = input.nextLine();

        System.out.print("Last name: ");
        String last = input.nextLine();

        System.out.print("Birth month: ");
        int m = input.nextInt();

        System.out.print("Birth day: ");
        int d = input.nextInt();

        System.out.print("Birth year: ");
        int y = input.nextInt();

        HeartRates person = new HeartRates(first, last, m, d, y);
        System.out.println("\n--- Results ---");
        person.displayInfo();
    }
}