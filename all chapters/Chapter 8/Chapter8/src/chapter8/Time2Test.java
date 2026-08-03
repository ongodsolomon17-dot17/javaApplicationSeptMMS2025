package chapter8;

public class Time2Test {
    public static void main(String[] args) {
        // --- Case A: Incrementing into the next minute ---
        System.out.println("=== Case A: Incrementing into the next minute ===");
        Time2 t1 = new Time2(6, 29, 59); // 06:29:59 AM
        System.out.printf("Before tick:       %s  (%s)%n", t1.toUniversalString(), t1.toString());
        t1.tick();
        System.out.printf("After tick:        %s  (%s)%n%n", t1.toUniversalString(), t1.toString());

        // --- Case B: Incrementing into the next hour ---
        System.out.println("=== Case B: Incrementing into the next hour ===");
        Time2 t2 = new Time2(9, 59, 59); // 09:59:59 AM
        System.out.printf("Before tick:       %s  (%s)%n", t2.toUniversalString(), t2.toString());
        t2.tick();
        System.out.printf("After tick:        %s  (%s)%n%n", t2.toUniversalString(), t2.toString());

        // --- Case C: Incrementing into the next day (Midnight rollover) ---
        System.out.println("=== Case C: Incrementing into the next day ===");
        Time2 t3 = new Time2(23, 59, 59); // 11:59:59 PM
        System.out.printf("Before tick:       %s  (%s)%n", t3.toUniversalString(), t3.toString());
        t3.tick();
        System.out.printf("After tick:        %s  (%s)%n%n", t3.toUniversalString(), t3.toString());

        // --- Additional Tests: incrementMinute & incrementHour ---
        System.out.println("=== Testing incrementMinute and incrementHour ===");
        Time2 t4 = new Time2(11, 58, 30); // 11:58:30 AM
        System.out.printf("Initial time:      %s  (%s)%n", t4.toUniversalString(), t4.toString());

        t4.incrementMinute(); // Should roll into 11:59:30 AM
        System.out.printf("After +1 minute:   %s  (%s)%n", t4.toUniversalString(), t4.toString());

        t4.incrementMinute(); // Should roll into 12:00:30 PM
        System.out.printf("After +1 minute:   %s  (%s)%n", t4.toUniversalString(), t4.toString());

        t4.incrementHour();   // Should roll into 01:00:30 PM
        System.out.printf("After +1 hour:     %s  (%s)%n", t4.toUniversalString(), t4.toString());
    }
}