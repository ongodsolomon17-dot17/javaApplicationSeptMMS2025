
package chapter8;

public class Date1 {
    private int month; // 1-12
    private int day;   // 1-31 based on month
    private int year;  // e.g., 1992, 2026

    private static final String[] monthNames = {
        "", "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    private static final int[] daysPerMonth = {
        0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    // Constructor 1: MM/DD/YYYY format (3 integers)
    public Date1(int month, int day, int year) {
        validateAndSet(month, day, year);
    }

    // Constructor 2: "Month DD, YYYY" format (String month, int day, int year)
    public Date1(String monthName, int day, int year) {
        int convertedMonth = convertMonthToNumber(monthName);
        validateAndSet(convertedMonth, day, year);
    }

    // Constructor 3: Day of year and Year (DDD YYYY - 2 integers)
    public Date1(int dayOfYear, int year) {
        if (year <= 0) {
            throw new IllegalArgumentException("Year must be greater than 0.");
        }

        int maxDaysInYear = isLeapYear(year) ? 366 : 365;
        if (dayOfYear <= 0 || dayOfYear > maxDaysInYear) {
            throw new IllegalArgumentException("Day of year out of range: " + dayOfYear);
        }

        this.year = year;

        // Convert day number of the year into month and day
        int currentMonth = 1;
        int remainingDays = dayOfYear;

        while (currentMonth <= 12) {
            int daysInCurrentMonth = getDaysInMonth(currentMonth, year);
            if (remainingDays <= daysInCurrentMonth) {
                this.month = currentMonth;
                this.day = remainingDays;
                break;
            }
            remainingDays -= daysInCurrentMonth;
            currentMonth++;
        }
    }

    // Helper: Validates and assigns instance variables
    private void validateAndSet(int month, int day, int year) {
        if (year <= 0) {
            throw new IllegalArgumentException("Year must be greater than 0.");
        }
        if (month <= 0 || month > 12) {
            throw new IllegalArgumentException("Month must be 1-12.");
        }
        if (day <= 0 || day > getDaysInMonth(month, year)) {
            throw new IllegalArgumentException("Day out of range for month and year.");
        }

        this.month = month;
        this.day = day;
        this.year = year;
    }

    // Helper: Converts Month String to 1-12 integer
    private int convertMonthToNumber(String monthName) {
        for (int i = 1; i < monthNames.length; i++) {
            if (monthNames[i].equalsIgnoreCase(monthName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid month name: " + monthName);
    }

    // Helper: Calculates day number within the year (DDD)
    private int calculateDayOfYear() {
        int dayOfYear = day;
        for (int m = 1; m < month; m++) {
            dayOfYear += getDaysInMonth(m, year);
        }
        return dayOfYear;
    }

    private static boolean isLeapYear(int year) {
        return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0));
    }

    private static int getDaysInMonth(int month, int year) {
        if (month == 2 && isLeapYear(year)) {
            return 29;
        }
        return daysPerMonth[month];
    }

    // Output Format A: MM/DD/YYYY
    public String toSlashString() {
        return String.format("%02d/%02d/%04d", month, day, year);
    }

    // Output Format B: Month DD, YYYY
    public String toLongDateString() {
        return String.format("%s %d, %d", monthNames[month], day, year);
    }

    // Output Format C: DDD YYYY
    public String toDayOfYearString() {
        return String.format("%03d %d", calculateDayOfYear(), year);
    }

    @Override
    public String toString() {
        return toSlashString();
    }
}