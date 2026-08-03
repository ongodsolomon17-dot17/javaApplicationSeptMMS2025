package chapter11;

public class CatchOrderDemo {

    public static void main(String[] args) {

        try {
            int[] numbers = new int[5];
            System.out.println(numbers[10]); 
        }
        catch (ArrayIndexOutOfBoundsException e) { 
            System.out.println("Caught ArrayIndexOutOfBoundsException");
        }
        catch (Exception e) { 
            System.out.println("Caught Exception");
        }
    }
}
