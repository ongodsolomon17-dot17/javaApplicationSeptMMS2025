public class UsingEnums {
	public static void main(String[] args) {
		DaysOfWeek day = DaysOfWeek.MONDAY;
		DaysOfWeek days = DaysOfWeekSunday;

		System.out.printf("Today is monday %s%n",days);
		System.out.printf("Today is  %s%n",days);
	}
}