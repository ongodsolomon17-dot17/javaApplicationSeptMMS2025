import java.util.Random;
import java.util.Scanner;

public class CrapsWager {

    static Random rand = new Random();

    public static int rollDice() {
        return rand.nextInt(6) + 1 + rand.nextInt(6) + 1;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int bankBalance = 1000;

        while (bankBalance > 0) {

            System.out.println("Bank Balance: $" + bankBalance);
            System.out.print("Enter wager: ");
            int wager = input.nextInt();

            while (wager > bankBalance) {
                System.out.print("Invalid wager. Enter again: ");
                wager = input.nextInt();
            }

            int sum = rollDice();
            System.out.println("Rolled: " + sum);

            boolean win;

            if (sum == 7 || sum == 11) win = true;
            else if (sum == 2 || sum == 3 || sum == 12) win = false;
            else {
                int point = sum;
                int newSum;

                do {
                    newSum = rollDice();
                } while (newSum != point && newSum != 7);

                win = (newSum == point);
            }

            if (win) {
                bankBalance += wager;
                System.out.println("You win!");
            } else {
                bankBalance -= wager;
                System.out.println("You lose!");
            }

            System.out.println("New balance: $" + bankBalance);

            if (bankBalance == 0)
                System.out.println("Sorry. You busted!");
        }

        input.close();
    }
}