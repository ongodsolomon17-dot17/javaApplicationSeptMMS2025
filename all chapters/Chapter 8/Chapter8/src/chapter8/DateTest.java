package chapter8;

public class DateTest {
    public static void main(String[] args) {
        // --- Case A: Incrementing into the next month ---
        System.out.println("=== Case A: Loop Testing Month Rollover ===");
        Date date1 = new Date(10, 29, 2026); // Oct 29, 2026
        
        System.out.println("Starting Date: " + date1);
        for (int i = 1; i <= 5; i++) {
            date1.nextDay();
            System.out.printf("Day %d tick: %s%n", i, date1);
        }

        System.out.println();

        // --- Case B: Incrementing into the next year ---
        System.out.println("=== Case B: Loop Testing Year Rollover ===");
        Date date2 = new Date(12, 29, 2026); // Dec 29, 2026
        
        System.out.println("Starting Date: " + date2);
        for (int i = 1; i <= 5; i++) {
            date2.nextDay();
            System.out.printf("Day %d tick: %s%n", i, date2);
        }

        System.out.println();

        // --- Validation Test: Invalid Year ---
        System.out.println("=== Testing Year Error Checking ===");
        try {
            Date invalidDate = new Date(5, 15, -2026);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught Expected Exception: " + e.getMessage());
        }
    }
}