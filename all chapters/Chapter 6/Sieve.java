public class Sieve {

    public static void main(String[] args) {

        boolean[] prime = new boolean[1000];

        for (int i = 2; i < prime.length; i++)
            prime[i] = true;

        for (int i = 2; i * i < prime.length; i++) {

            if (prime[i]) {

                for (int j = i * i; j < prime.length; j += i)
                    prime[j] = false;
            }
        }

        System.out.println("Prime Numbers:");

        for (int i = 2; i < prime.length; i++) {

            if (prime[i])
                System.out.print(i + " ");
        }
    }
}