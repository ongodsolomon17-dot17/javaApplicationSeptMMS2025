package chapter8;

public class Date {
    private int month; // 1-12
    private int day;   // 1-31 based on month
    private int year;  // must be greater than 0

    private static final int[] daysPerMonth =
        { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    // Constructor: validates month, day, AND year
    public Date(int month, int day, int year) {
        // Validate year
        if (year <= 0) {
            throw new IllegalArgumentException("year (" + year + ") must be greater than 0");
        }

        // Validate month
        if (month <= 0 || month > 12) {
            throw new IllegalArgumentException("month (" + month + ") must be 1-12");
        }

        // Validate day for the given month
        if (day <= 0 || (day > daysPerMonth[month] && !(month == 2 && day == 29))) {
            throw new IllegalArgumentException("day (" + day + ") out-of-range for specified month/year");
        }

        // Check for leap year if month is 2 and day is 29
        if (month == 2 && day == 29 && !isLeapYear(year)) {
            throw new IllegalArgumentException("day (" + day + ") out-of-range for specified month/year");
        }

        this.month = month;
        this.day = day;
        this.year = year;
    }

    // Increments the day by 1, correctly transitioning months and years
    public void nextDay() {
        if (day < getDaysInMonth(month, year)) {
            day++; // Advance standard day
        } else {
            day = 1; // Reset to 1st day of new month
            if (month < 12) {
                month++; // Advance month
            } else {
                month = 1; // Reset to January
                year++;    // Advance to next year
            }
        }
    }

    // Helper method to check for leap years
    private static boolean isLeapYear(int year) {
        return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0));
    }

    // Helper method to determine total days in a month
    private static int getDaysInMonth(int month, int year) {
        if (month == 2 && isLeapYear(year)) {
            return 29;
        }
        return daysPerMonth[month];
    }

    // String formatting (month/day/year)
    public String toString() {
        return String.format("%d/%d/%d", month, day, year);
    }
}