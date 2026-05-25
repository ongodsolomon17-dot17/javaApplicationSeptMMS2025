//.3.38 
mport java.util.Scanner;

public class Encryptor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a 4-digit integer to encrypt: ");
        int number = scanner.nextInt();

        int d1 = (number / 1000 + 7) % 10;
        int d2 = (number / 100 % 10 + 7) % 10;
        int d3 = (number / 10 % 10 + 7) % 10;
        int d4 = (number % 10 + 7) % 10;

        // Swap 1st with 3rd, and 2nd with 4th
        int encrypted = d3 * 1000 + d4 * 100 + d1 * 10 + d2;
        
        System.out.printf("Encrypted number: %04d%n", encrypted);
        scanner.close();
    }
}

public class Decryptor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a 4-digit integer to decrypt: ");
        int encrypted = scanner.nextInt();

        int d3 = encrypted / 1000;
        int d4 = encrypted / 100 % 10;
        int d1 = encrypted / 10 % 10;
        int d2 = encrypted % 10;

        // Reverse the + 7 % 10 scheme
        d1 = (d1 >= 7) ? (d1 - 7) : (d1 + 10 - 7);
        d2 = (d2 >= 7) ? (d2 - 7) : (d2 + 10 - 7);
        d3 = (d3 >= 7) ? (d3 - 7) : (d3 + 10 - 7);
        d4 = (d4 >= 7) ? (d4 - 7) : (d4 + 10 - 7);

        int original = d1 * 1000 + d2 * 100 + d3 * 10 + d4;

        System.out.printf("Decrypted number: %04d%n", original);
        scanner.close();
    }
}