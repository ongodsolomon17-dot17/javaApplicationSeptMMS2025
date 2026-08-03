public class HeartRates {
    private String firstName;
    private String lastName;
    private int birthMonth;
    private int birthDay;
    private int birthYear;

    public HeartRates(String firstName, String lastName,
                      int month, int day, int year) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthMonth = month;
        this.birthDay = day;
        this.birthYear = year;
    }

    public int getAge() {
        int currentYear = java.time.Year.now().getValue();
        return currentYear - birthYear;
    }

    public int getMaxHeartRate() {
        return 220 - getAge();
    }

    public String getTargetHeartRate() {
        int max = getMaxHeartRate();
        return (int)(max * 0.5) + " - " + (int)(max * 0.85);
    }

    public void displayInfo() {
        System.out.println(firstName + " " + lastName);
        System.out.println("DOB: " + birthMonth + "/" + birthDay + "/" + birthYear);
        System.out.println("Age: " + getAge());
        System.out.println("Max HR: " + getMaxHeartRate());
        System.out.println("Target HR: " + getTargetHeartRate());
    }
}