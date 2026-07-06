public class PassingMultiArrayToMethod {
	public static int total(int[][] numbers){
		int sum = 0;
		for (int row = 0; row < numbers.length; row++){
			for (int col = 0; col < numbers.length ; col++){
				sum += numbers[row][col];
			}
		}
		return sum;
	}
	public static void main(String[] args){
		int [][] arr = {
			{7, 8, 9, 5},
			{2, 3, 4, 5},
			{4, 5, 6, 7},
			{9, 2, 4, 3}
		};
		int sum = total(arr);
		System.out.printf("The sum of the numbes is: %d%n ",sum);
		
	}
}