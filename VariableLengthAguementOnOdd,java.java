public class VariableLengthAguementOnOdd{
	public static void main(String[] args){
		System.out.printf("The odd numbers are %d%n",addition(5,6,8,9,3,2,1));
		System.out.printf("The odd numbers are %d%n",addition(8,6,4,7,5,6));
		System.out.printf("The odd numbers are %d%n",addition(5,6,8,9,4));
		System.out.printf("The odd numbers are %d%n",addition(5,6,8,6));
		System.out.printf("The odd numbers are %d%n",addition(5,6,8));
		System.out.printf("The odd numbers are %d%n",addition(5,6));
		System.out.printf("The odd numbers are %d%n",addition(5));






		
	}
	public static int addition(int...numbers){
		int oddNumbers = 0;
		for(int number : numbers){
			if(number % 2 != 0){
				oddNumbers += number;
			}
		}
		return oddNumbers;

	}
	
}