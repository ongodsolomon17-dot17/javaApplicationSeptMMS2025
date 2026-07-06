public class MultiDimensionalArrayAssignment {
	public static void main(String[] args) {

        int[][] numbers = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 4},
            {7, 2, 3, 1, 5}
        };
		Sytstem.out.println("This are ur numbers on a matrix format: ");

        for (int row = 0; row < numbers.length; row++) {
            for (int col = 0; col < numbers[row].length; col++) {
                System.out.print(numbers[row][col] + "\t");
            }
            System.out.println(); 
        }
    }
}