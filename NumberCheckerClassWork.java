import java.util.Scanner;
public class NumberCheckerClassWork{
	public static void main(String[] args){
		Scanner input = new SCanner(System.in);
		int num1;
		int num2;
		int num3;
		int num4;
		int num5;
		int num6;
		
		
		System.out.print("Enter first number:");
		num1 = input.nextlnt();
		
		System.out.print("Enter second number:");
		num2 = input.nextlnt();
		
		System.out.print("Enter third number:");
		num3 = input.nextlnt();
		
		int sum = num1+num2+num3;
		System.out.printf("The sum is: %d%n",sum);
		
		if(sum%2==0 &&10==0){
			System.out.println("successful");
			
			System.out.print("Enter first number:");
			num4 = input.nextlnt();
			
			System.out.print("Enter second number:");
			num5 = input.nextlnt();
			
		}
		
	}
	
}
		
	