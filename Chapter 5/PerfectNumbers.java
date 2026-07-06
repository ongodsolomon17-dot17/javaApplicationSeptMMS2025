public class PerfectNumbers {

    public static boolean isPerfect(int number) {

        int sum = 0;

        for (int i = 1; i < number; i++) {
            if (number % i == 0) {
                sum += i;
            }
        }

        return sum == number;
    }

    public static void main(String[] args) {

        for (int num = 1; num <= 1000; num++) {

            if (isPerfect(num)) {
                System.out.print(num + " factors: ");

                for (int i = 1; i < num; i++) {
                    if (num % i == 0) {
                        System.out.print(i + " ");
                    }
                }

                System.out.println();
            }
        }
    }
}