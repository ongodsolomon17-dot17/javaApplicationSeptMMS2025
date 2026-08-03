import java.util.Random;
import java.util.Scanner;

public class GuessTheNumberCount {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Random rand = new Random();
        char play;

        do {
            int number = rand.nextInt(1000) + 1;
            int guess;
            int count = 0;

            do {
                System.out.println("Guess a number between 1 and 1000:");
                guess = input.nextInt();
                count++;

                if (guess > number)
                    System.out.println("Too high. Try again.");
                else if (guess < number)
                    System.out.println("Too low. Try again.");
                else
                    System.out.println("Correct!");

            } while (guess != number);

            System.out.println("Total guesses: " + count);

            if (count <= 10)
                System.out.println("Either you know the secret or you got lucky!");
            else
                System.out.println("You should be able to do better!");

            System.out.print("Play again? (y/n): ");
            play = input.next().charAt(0);

        } while (play == 'y' || play == 'Y');

        input.close();
    }
}