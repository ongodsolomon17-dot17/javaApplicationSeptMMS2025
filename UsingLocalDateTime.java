import java.time.LocalDateTime;

public class UsingLocalDateTime{
	public static void main(String[] args){
		LocalDateTime todayDateAndTime = LocalDateTime.now();
		System.out.printf("The current date and times is %s%n",todayDateAndTime);
	}
}
