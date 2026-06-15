public class TransverseEven {
    public static void main(String[] args) {
        int[] numbers = {6, 9, 12, 8, 2, 16, 14};
		
		int totalEven = 0;
        for (int i = 0; i < numbers.length; i++) {
            System.out.printf("%d%n", numbers[i]);
if (numbers[i] % 2 == 0) {
                totalEven++;
            } 
            
        }
		System.out.printf("The count of even numbers is %d%n",totalEven);

    }
}