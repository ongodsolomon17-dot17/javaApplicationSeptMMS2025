import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Random rand = new Random();
        char play;

        do {
            int number = rand.nextInt(1000) + 1;
            int guess;

            System.out.println("Guess a number between 1 and 1000:");

            do {
                guess = input.nextInt();

                if (guess > number)
                    System.out.println("Too high. Try again.");
                else if (guess < number)
                    System.out.println("Too low. Try again.");
                else
                    System.out.println("Congratulations. You guessed the number!");

            } while (guess != number);

            System.out.print("Play again? (y/n): ");
            play = input.next().charAt(0);

        } while (play == 'y' || play == 'Y');

        input.close();
    }
}