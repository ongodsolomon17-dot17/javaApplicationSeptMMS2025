public class StudentGradingSystem{
	public static void main(String[] args){
		String[] names = {"John","Jack","Mary","James","Lucy"};
		int[] marks = {69,80,100,70,50};
		
		for(int i = 0; i < 5;i++){
		if(mark >= 70){
			System.out.printf("Student Name:%s%n",names[i]);
			System.out.printf("Student Mark :%d%n",marks[i]);
			System.out.println("Student Grade : A");
			System.out.println("===============================\n");

		}
		else if(marks[i] >= 60){
			System.out.printf("Student Name:%s%n",names[i]);
			System.out.printf("Student Mark :%d%n",marks[i]);
			System.out.println("Student Grade : B");
			System.out.println("===============================\n");

		}
		else if(marks[i] >= 50){
			System.out.printf("Student Name:%s%n",names[i]);
			System.out.printf("Student Mark :%d%n",marks[i]);
			System.out.println("Student Grade : C");
			System.out.println("===============================\n");

		}
		else if(marks[i] >= 40){
			System.out.printf("Student Name:%s%n",names[i]);
			System.out.printf("Student Mark :%d%n",marks[i]);
			System.out.println("Student Grade : D");
			System.out.println("===============================\n");

		}
		else{
			System.out.printf("Student Name:%s%n",names[i]);
			System.out.printf("Student Mark :%d%n",marks[i]);
			System.out.print("Student Grade : F");
		}
		}//Traditional for loop ends here

	}
}