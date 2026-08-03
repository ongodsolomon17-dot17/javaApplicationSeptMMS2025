package chapter8;

public class Time2 {
    private int totalSeconds; // Total seconds since midnight (0 - 86399)

    // Constructors
    public Time2() {
        this(0, 0, 0);
    }

    public Time2(int hour) {
        this(hour, 0, 0);
    }

    public Time2(int hour, int minute) {
        this(hour, minute, 0);
    }

    public Time2(int hour, int minute, int second) {
        setTime(hour, minute, second);
    }

    public Time2(Time2 time) {
        this(time.getHour(), time.getMinute(), time.getSecond());
    }

    // Set Methods
    public void setTime(int hour, int minute, int second) {
        if (hour < 0 || hour >= 24) {
            throw new IllegalArgumentException("hour must be 0-23");
        }
        if (minute < 0 || minute >= 60) {
            throw new IllegalArgumentException("minute must be 0-59");
        }
        if (second < 0 || second >= 60) {
            throw new IllegalArgumentException("second must be 0-59");
        }

        this.totalSeconds = hour * 3600 + minute * 60 + second;
    }

    public void setHour(int hour) {
        if (hour < 0 || hour >= 24) {
            throw new IllegalArgumentException("hour must be 0-23");
        }
        setTime(hour, getMinute(), getSecond());
    }

    public void setMinute(int minute) {
        if (minute < 0 || minute >= 60) {
            throw new IllegalArgumentException("minute must be 0-59");
        }
        setTime(getHour(), minute, getSecond());
    }

    public void setSecond(int second) {
        if (second < 0 || second >= 60) {
            throw new IllegalArgumentException("second must be 0-59");
        }
        setTime(getHour(), getMinute(), second);
    }

    // Increment Methods
    
    // Increments time by 1 second. Rolls over at 86400 seconds (midnight)
    public void tick() {
        this.totalSeconds = (this.totalSeconds + 1) % 86400;
    }

    // Increments minute by 1 (60 seconds)
    public void incrementMinute() {
        for (int i = 0; i < 60; i++) {
            tick();
        }
    }

    // Increments hour by 1 (3600 seconds)
    public void incrementHour() {
        for (int i = 0; i < 3600; i++) {
            tick();
        }
    }

    // Get Methods
    public int getHour() {
        return totalSeconds / 3600;
    }

    public int getMinute() {
        return (totalSeconds % 3600) / 60;
    }

    public int getSecond() {
        return totalSeconds % 60;
    }

    // Formatting Methods
    public String toUniversalString() {
        return String.format("%02d:%02d:%02d", getHour(), getMinute(), getSecond());
    }

    public String toString() {
        return String.format("%d:%02d:%02d %s",
            ((getHour() == 0 || getHour() == 12) ? 12 : getHour() % 12),
            getMinute(), getSecond(), (getHour() < 12 ? "AM" : "PM"));
    }
}