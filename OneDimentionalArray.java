public class OneDimentionalArray {
	public static void main(String[] args) {
		
		int[] numbers = {2,46,8,10,12,15,20,25,26,30};
		System.out.printf("The element at the index number 5 is %d%n",numbers[5]);
		System.out.printf("The element at the index number 7 is %d%n",numbers[25]);
		
		System.out.println("Trasversing through the element of the array");
		System.out.print("===============================================");
		
		for (int i = 0; i < 10; i++){
			System.out.printf("%d%n",numbers[i]);
		}
	}
}