import java.util.Scanner;

public class UserInput{
	public static void main(String[] agrs){
		Scanner input = new Scanner (System.in);
		
		System.out.print("Enter your name:");
		String name = input.nextLine();
		
		System.out.printf("Hello %s, you're welcomed to NIIT", name);
	}
}		