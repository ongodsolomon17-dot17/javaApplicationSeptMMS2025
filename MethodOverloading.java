import java.util.Scanner;
public class MethodOverloading{
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		
		System.out.println("[=============Calculate the perimeter of shapes=================");
		System.out.println("Enter 1. for Square : ");
		System.out.println("Enter 2. for Rectangle:  ");
		System.out.println("Enter 3. for Triangle: ");
		System.out.println("Enter 4.to exit : ");
		
				System.out.print("Enter your choice:  ");

		int choice =scan.nextInt();
		switch(choice){
			case 1:
			System.out.print("Enter the length of the square: ");
			int lenOfSquare = scan.nextInt();
			perimeter(lenOfSquare);
			break;
			
			case 2:
			System.out.print("Enter the length of the rectangle: ");
			int lenOfRec =scan.nextInt();
			
			System.out.print("Enter the width of the rectangle: ");
			int widthOfRec = scan.nextInt();
			perimeter(lenOfRec);
			break;
			
			case 3:
			System.out.print("Enter the side A: ");
			int sideA =scan.nextInt();
			
			System.out.print("Enter the side B: ");
			int sideB =scan.nextInt();
			
			System.out.print("Enter the side C: ");
			int sideC = scan.nextInt();
			perimeter(sideA,sideB,sideC);
			break;
			
			default:
						System.out.println("Program Exited,GOODBYE..............");
							System.out.println("=============Thank YOU=================");

		}
		}
	
	public static void perimeter(int lenOfSquare){
		int perimeterofSquare = 4 * lenOfSquare;
		System.out.printf("THe perimeter if the sqaure is %d%n",perimeterofSquare);
	}
		public static void perimeter(int lenOfRec,int widthOfRec){
		 int perimeterofRec = 2 * (lenOfRec + widthOfRec);
		System.out.printf("THe perimeter of the sqaure is %d%n",perimeterofRec);
	}
	public static void perimeter(int sideA,int sideB,int sideC){
		 int perimeterOfTriangle = sideA + sideB +sideC;
		System.out.printf("THe perimeter of the triangle is %d%n",perimeterOfTriangle);

	}

}