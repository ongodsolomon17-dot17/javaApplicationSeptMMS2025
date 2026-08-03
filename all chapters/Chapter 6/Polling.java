import java.util.Scanner;

public class Polling {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String[] topics = {
            "Education",
            "Healthcare",
            "Environment",
            "Security",
            "Employment"
        };

        int[][] responses = new int[5][10];

        for (int person = 1; person <= 5; person++) {

            System.out.println("Person " + person);

            for (int i = 0; i < topics.length; i++) {

                System.out.print(topics[i] + " (1-10): ");

                int rating = input.nextInt();

                responses[i][rating - 1]++;
            }
        }

        System.out.println("\nTopic\t1 2 3 4 5 6 7 8 9 10 Avg");

        int highest = Integer.MIN_VALUE;
        int lowest = Integer.MAX_VALUE;
        String highTopic = "";
        String lowTopic = "";

        for (int i = 0; i < topics.length; i++) {

            int total = 0;
            int votes = 0;

            System.out.print(topics[i] + "\t");

            for (int j = 0; j < 10; j++) {

                System.out.print(responses[i][j] + " ");

                total += responses[i][j] * (j + 1);
                votes += responses[i][j];
            }

            double average = (double) total / votes;

            System.out.printf("%.2f%n", average);

            if (total > highest) {
                highest = total;
                highTopic = topics[i];
            }

            if (total < lowest) {
                lowest = total;
                lowTopic = topics[i];
            }
        }

        System.out.println("\nHighest Rated: " + highTopic + " (" + highest + ")");
        System.out.println("Lowest Rated : " + lowTopic + " (" + lowest + ")");

        input.close();
    }
}