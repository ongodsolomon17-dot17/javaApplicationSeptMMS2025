
package chapter8;

public class DateAndTimeTest {
    public static void main(String[] args) {
        // --- Test 1: Tick into the next day ---
        System.out.println("=== Test 1: Ticking into Next Day ===");
        DateAndTime dt1 = new DateAndTime(5, 31, 2026, 23, 59, 59); // May 31, 2026 11:59:59 PM
        System.out.println("Before tick:       " + dt1);
        dt1.tick(); // Should advance to June 1, 2026 12:00:00 AM
        System.out.println("After tick:        " + dt1);

        System.out.println();

        // --- Test 2: Ticking into the next year ---
        System.out.println("=== Test 2: Ticking into Next Year ===");
        DateAndTime dt2 = new DateAndTime(12, 31, 2026, 23, 59, 59); // Dec 31, 2026 11:59:59 PM
        System.out.println("Before tick:       " + dt2);
        dt2.tick(); // Should advance to Jan 1, 2027 12:00:00 AM
        System.out.println("After tick:        " + dt2);

        System.out.println();

        // --- Test 3: Incrementing hour into next day ---
        System.out.println("=== Test 3: Incrementing Hour into Next Day ===");
        DateAndTime dt3 = new DateAndTime(3, 15, 2026, 23, 30, 0); // Mar 15, 2026 11:30:00 PM
        System.out.println("Before +1 hour:    " + dt3);
        dt3.incrementHour(); // Should advance to Mar 16, 2026 12:30:00 AM
        System.out.println("After +1 hour:     " + dt3);
    }
}
