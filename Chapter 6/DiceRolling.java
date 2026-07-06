import java.util.Random;

public class DiceRolling {

    public static void main(String[] args) {

        Random random = new Random();

        int[] frequency = new int[13];

        for (int i = 1; i <= 36000000; i++) {

            int die1 = random.nextInt(6) + 1;
            int die2 = random.nextInt(6) + 1;

            frequency[die1 + die2]++;
        }

        System.out.println("Sum\tFrequency");

        for (int i = 2; i <= 12; i++)
            System.out.println(i + "\t" + frequency[i]);
    }
}