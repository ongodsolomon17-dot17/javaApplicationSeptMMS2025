
package chapter8;

public class DateAndTime {
    private Date date;
    private Time2 time;

    // Constructor accepting individual date and time components
    public DateAndTime(int month, int day, int year, int hour, int minute, int second) {
        this.date = new Date(month, day, year);
        this.time = new Time2(hour, minute, second);
    }

    // Default constructor (1/1/2026 00:00:00)
    public DateAndTime() {
        this(1, 1, 2026, 0, 0, 0);
    }

    // Tick method: increments 1 second and advances date if midnight is passed
    public void tick() {
        int previousHour = time.getHour();
        time.tick();
        
        // If hours wrapped from 23 to 0, advance the day
        if (previousHour == 23 && time.getHour() == 0) {
            date.nextDay();
        }
    }

    // Increment minute by 1
    public void incrementMinute() {
        for (int i = 0; i < 60; i++) {
            tick();
        }
    }

    // Increment hour by 1 (calls nextDay via tick if incrementing into next day)
    public void incrementHour() {
        for (int i = 0; i < 3600; i++) {
            tick();
        }
    }

    // Output formatted as Date + Universal Time (e.g., "12/31/2026 23:59:59")
    public String toUniversalString() {
        return String.format("%s %s", date.toString(), time.toUniversalString());
    }

    // Output formatted as Date + Standard Time (e.g., "12/31/2026 11:59:59 PM")
    public String toString() {
        return String.format("%s %s", date.toString(), time.toString());
    }
}