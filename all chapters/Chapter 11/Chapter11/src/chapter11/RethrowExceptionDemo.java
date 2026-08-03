package chapter11;

public class RethrowExceptionDemo {

    public static void someMethod() throws Exception {
        try {
            someMethod2();
        } catch (Exception e) {
            System.out.println("Exception caught in someMethod. Rethrowing...");
            throw e;   // Rethrow the exception
        }
    }

    public static void someMethod2() throws Exception {
        throw new Exception("Exception thrown from someMethod2");
    }

    public static void main(String[] args) {

        try {
            someMethod();
        } catch (Exception e) {
            System.out.println("Exception caught in main.");
            System.out.println("\nStack Trace:");
            e.printStackTrace();
        }
    }
}
