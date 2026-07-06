import java.util.Scanner;

public class QualityPoints {

    public static int qualityPoints(double avg) {

        if (avg >= 90) return 4;
        else if (avg >= 80) return 3;
        else if (avg >= 70) return 2;
        else if (avg >= 60) return 1;
        else return 0;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter average: ");
        double avg = input.nextDouble();

        System.out.println("Quality points: " + qualityPoints(avg));

        input.close();
    }
}