import java.util.Queue;
import java.util.LinkedList;

public class UsingQueue{
	public static void main(String[] args){
		Queue<String> names = new LinkedList<>();
		
		names.add("Henry");
		names.add("Young");
		names.add("john");
		names.offer("peter");
		
		System.out.println(names);
		System.out.println(names.poll());
		System.out.println(names);
		System.out.println(names.peek());
		System.out.println(names);
		System.out.printf("if my Queue is empty say True if it aint say false:  \n %b%n",names.isEmpty());
	}
}