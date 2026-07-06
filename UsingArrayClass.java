import java.util.Arrays;
public class UsingArrayClass{
	public static void main(String[] args){
		int[] numbers = {5,8,2,3,9,4,1,6,7,10};
		int[] a = {7,9,6};
		int[] b = {7,9,6};
		
		int[] c = {7,9,9};
		int[] d = {7,9,6};
		
		Arrays.sort(numbers);
		System.out.println("The elemnts of the arrays are:");
		
		for (int number : numbers){
			System.out.printf("%d%n",number);
		}
		System.out.println("Binarysearch");
		int index = Arrays.binarySearch(numbers,9);
		System.out.printf("The index number of 9 is %d%n",index);
		
		boolean isEqual = Arrays.equals(a,b);
		System.out.printf("The result is: %b%n",isEqual);
		
		int isCompare = Arrays.compare(c,d);
		System.out.printf("The result is: %d%n",isCompare);
		
		int[] fillnumbers = new int [7];
		Arrays.fill(fillnumbers,12);
		System.out.println("these are the lists of the filled numbers: " + Arrays.toString(fillnumbers));
		
		int[] mainCopy = {1,2,3};
		int[] theCopyOfMainCopy = Arrays.copyOf(mainCopy,6);
		System.out.println("These are the lists of the copied numbers: " + Arrays.toString(theCopyOfMainCopy));
		
		int[][] deepArray = {
		{2,3,4},
		{4,5,6},
		{1,0,9}};

		System.out.println("These is are the strings: " + Arrays.deepToString(deepArray));  
		
		
	}
}