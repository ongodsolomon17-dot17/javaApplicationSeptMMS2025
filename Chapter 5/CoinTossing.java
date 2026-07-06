import java.util.Random;
import java.util.Scanner;

enum Coin { HEADS, TAILS }

public class CoinTossing {

    public static Coin flip() {
        Random rand = new Random();
        return rand.nextInt(2) == 0 ? Coin.HEADS : Coin.TAILS;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int heads = 0, tails = 0;
        char choice;

        do {
            System.out.print("Toss coin? (y/n): ");
            choice = input.next().charAt(0);

            if (choice == 'y' || choice == 'Y') {
                Coin result = flip();

                System.out.println("Result: " + result);

                if (result == Coin.HEADS) heads++;
                else tails++;

                System.out.println("Heads: " + heads + " Tails: " + tails);
            }

        } while (choice == 'y' || choice == 'Y');

        input.close();
    }
}