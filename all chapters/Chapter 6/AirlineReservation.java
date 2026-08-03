import java.util.Scanner;

public class AirlineReservation {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        boolean[] seats = new boolean[10];

        while (true) {

            System.out.print("Type 1 for First Class or 2 for Economy (0 to quit): ");
            int choice = input.nextInt();

            if (choice == 0)
                break;

            boolean assigned = false;

            if (choice == 1) {

                for (int i = 0; i < 5; i++) {

                    if (!seats[i]) {

                        seats[i] = true;

                        System.out.println("Boarding Pass");
                        System.out.println("Seat: " + (i + 1));
                        System.out.println("First Class");

                        assigned = true;
                        break;
                    }
                }

            } else {

                for (int i = 5; i < 10; i++) {

                    if (!seats[i]) {

                        seats[i] = true;

                        System.out.println("Boarding Pass");
                        System.out.println("Seat: " + (i + 1));
                        System.out.println("Economy");

                        assigned = true;
                        break;
                    }
                }
            }

            if (!assigned)
                System.out.println("Next flight leaves in 3 hours.");
        }

        input.close();
    }
}