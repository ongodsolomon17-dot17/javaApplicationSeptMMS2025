
package chapter8;

public class Date1Test {
    public static void main(String[] args) {
        // Test Constructor 1: Three integers (MM/DD/YYYY)
        Date1 d1 = new Date1(6, 14, 1992);

        // Test Constructor 2: String month, int day, int year
        Date1 d2 = new Date1("June", 14, 1992);

        // Test Constructor 3: Day of year and Year (DDD YYYY) -> Day 166 of 1992 is June 14
        Date1 d3 = new Date1(166, 1992);

        System.out.println("=== Testing Constructor 1 (6, 14, 1992) ===");
        System.out.println("Format A (MM/DD/YYYY): " + d1.toSlashString());
        System.out.println("Format B (Month DD, YYYY): " + d1.toLongDateString());
        System.out.println("Format C (DDD YYYY): " + d1.toDayOfYearString());

        System.out.println("\n=== Testing Constructor 2 (\"June\", 14, 1992) ===");
        System.out.println("Format A: " + d2.toSlashString());
        System.out.println("Format B: " + d2.toLongDateString());
        System.out.println("Format C: " + d2.toDayOfYearString());

        System.out.println("\n=== Testing Constructor 3 (Day 166, 1992) ===");
        System.out.println("Format A: " + d3.toSlashString());
        System.out.println("Format B: " + d3.toLongDateString());
        System.out.println("Format C: " + d3.toDayOfYearString());
    }
}
