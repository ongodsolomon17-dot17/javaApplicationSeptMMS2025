import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UsingDateTimeFormatter{
	public static void main(String[] args){
		LocalDate today = LocalDate.now();
		
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		
		System.out.println(today.format(formatter1));
		System.out.println(today.format(formatter2));

		LocalDateTime now = LocalDateTime.now();
		
		System.out.println("dd : "+
				now.format(DateTimeFormatter.ofPattern("dd")));
				
		System.out.println("MM : "+
				now.format(DateTimeFormatter.ofPattern("MM")));
				
		System.out.println("MMM : "+
				now.format(DateTimeFormatter.ofPattern("MMM")));
				
		System.out.println("MMMM : "+
				now.format(DateTimeFormatter.ofPattern("MMMM")));
				
		System.out.println("yy : "+
				now.format(DateTimeFormatter.ofPattern("yy")));
				
		System.out.println("yyyy : "+
				now.format(DateTimeFormatter.ofPattern("yyyy")));
				
		System.out.println("HH : "+
				now.format(DateTimeFormatter.ofPattern("HH")));
				
		System.out.println("hh : "+
				now.format(DateTimeFormatter.ofPattern("hh")));
				
		System.out.println("mm : "+
				now.format(DateTimeFormatter.ofPattern("mm")));
				
		System.out.println("ss : "+
				now.format(DateTimeFormatter.ofPattern("ss")));
				
		System.out.println("a : "+
				now.format(DateTimeFormatter.ofPattern("a")));
	}
}