import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class UsingList2{
    public static void main(String[] args){
        List<Integer> numbers = new ArrayList<>();

        numbers.add(90);
        numbers.add(30);
        numbers.add(70);
        numbers.add(80);
        numbers.add(10);
        numbers.add(40);
        numbers.add(20);
        numbers.add(60);
        numbers.add(90);

        System.out.println( numbers);
        System.out.printf("The size of the list is: %d\n%n", numbers.size());
        }
}