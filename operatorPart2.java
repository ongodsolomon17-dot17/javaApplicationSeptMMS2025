public class operatorPart2{
	public static void main(String[] args){
		//Logical operators (&&,\\,!)
		int num1 = 15;
		int num2 = 30;
		int num3 = 18;
		
		boolean andOperator = (num1 > num2) && (num1 > num3);
		System.out.printf("is (%d > %d) && (%d > %d): %b%n", num1,num2,num3);
		
		boolean ordOperator = (num1 > num2) || (num1 > num3);
		System.out.printf("is (%d > %d) || (%d >%d): %b%n", num1,num2,num3);
		
		boolean andOperator = (num1 > num2) && (num1 > num3);
		System.out.printf("is (%d > %d) && (%d > %d): %b%n", num1,num2,num3);
	

	}
}