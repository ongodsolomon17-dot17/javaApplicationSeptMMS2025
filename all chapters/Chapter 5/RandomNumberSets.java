public class RandomNumberSets {
    public static void main(String[] args) {

        // a) 2, 4, 6, 8, 10
        int a = 2 + (int) (Math.random() * 5) * 2;

        // b) 3, 5, 7, 9, 11
        int b = 3 + (int) (Math.random() * 5) * 2;

        // c) 6, 10, 14, 18, 22
        int c = 6 + (int) (Math.random() * 5) * 4;

        // Display the results
        System.out.println("a) Random number from {2, 4, 6, 8, 10}: " + a);
        System.out.println("b) Random number from {3, 5, 7, 9, 11}: " + b);
        System.out.println("c) Random number from {6, 10, 14, 18, 22}: " + c);
    }
}