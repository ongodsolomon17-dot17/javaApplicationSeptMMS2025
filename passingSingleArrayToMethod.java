public class passingSingleArrayToMethod {
	public static int sumTotal(int[] numbers){
			int sum = 0;
			for(int i = 0; i < numbers.length; i++){
				sum += numbers[i];
			}
			return sum;
			
		}
	public static void main(String[] args) {
		
		int[] arr = {7, 8, 4, 3, 2, 90, 1, 6, 8};
		int totalNumber = sumTotal(arr);
		System.out.printf("The total number is %d%n",totalNumber);
		
		
	}
}