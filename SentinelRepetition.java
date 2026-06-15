import java.util.Scanner;

public class  SentinelRepetition{
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int num;
		int sum = 0;
		
		while(true){
			System.out.print("Enter number: ");
			num = scan.nextInt();
			
			if(num == -1) {
				break;
				
			}	

			sum += num;	
		}
		System.out.printf("The sum of all the numbers is %d%n",sum);
	}
}