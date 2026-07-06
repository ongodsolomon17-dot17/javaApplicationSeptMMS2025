import java.time.LocalTime;
public class UsingLocalTime{
	public static void main(String[] args){
		LocalTime time = LocalTime.now();
		LocalTime meetingTime = LocalTime.of(11,40,20);
		LocalTime classTime = LocalTime.parse("02:10:01");
		boolean isBefore = meetingTime.isBefore(classTime);
		boolean isAfter = meetingTime.isAfter(classTime);
		
		System.out.printf("The time is %s%n",time);
		System.out.printf("The meeting time is: %s%n",meetingTime);
		System.out.printf("Our class time will be %s on monday %n",classTime);
		System.out.printf("The time is %s%n",time);
		System.out.printf("is %s before %s ? %b%n",meetingTime,classTime,isBefore);
		System.out.printf("is %s after %s ? %b%n",meetingTime,classTime,isAfter);
		
	}
}