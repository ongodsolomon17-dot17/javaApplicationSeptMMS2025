package chapter11;

public class ExceptionPropagationDemo {

    public static void method1() {
        try {
            System.out.println("Inside method1");
            method2();
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException caught in method1");
        }
    }

    public static void method2() {
        String str = null;
        System.out.println(str.length()); 
    }

    public static void main(String[] args) {

        try {
            method1();
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught in main");
        }

        System.out.println("Program continues...");
    }
}