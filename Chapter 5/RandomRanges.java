public class RandomRanges {
    public static void main(String[] args) {

        // a) 1 ≤ n ≤ 2
        int a = (int) (Math.random() * 2) + 1;

        // b) 1 ≤ n ≤ 100
        int b = (int) (Math.random() * 100) + 1;

        // c) 0 ≤ n ≤ 9
        int c = (int) (Math.random() * 10);

        // d) 1000 ≤ n ≤ 1112
        int d = (int) (Math.random() * 113) + 1000;

        // e) –1 ≤ n ≤ 1
        int e = (int) (Math.random() * 3) - 1;

        // f) –3 ≤ n ≤ 11
        int f = (int) (Math.random() * 15) - 3;

        // Display the results
        System.out.println("a) 1 ≤ n ≤ 2: " + a);
        System.out.println("b) 1 ≤ n ≤ 100: " + b);
        System.out.println("c) 0 ≤ n ≤ 9: " + c);
        System.out.println("d) 1000 ≤ n ≤ 1112: " + d);
        System.out.println("e) -1 ≤ n ≤ 1: " + e);
        System.out.println("f) -3 ≤ n ≤ 11: " + f);
    }
}