import java.util.Scanner;
public class EvenOrOddChecker{
	public static void main(String[] args ){
	Scanner scan = new Scanner(System.in);
	char option;
	do{
		System.out.printf("enter any number: ");
		int num = scan.nextInt();
		
		if(num % 2 == 0){
			System.out.printf("The number is an even number");
		}
			else{
				System.out.printf("The number is an odd number");
			}
			System.out.print("Do you want to run the program again(Y/N): ");
			option = scan.next().charAt(0);
			
		} while(option == 'Y' || option == 'y');
		System.out.println("Good bye.....................................");
	}
}