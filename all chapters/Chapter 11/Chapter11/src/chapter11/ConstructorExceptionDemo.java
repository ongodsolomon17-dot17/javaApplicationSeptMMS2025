package chapter11;

class SomeClass {

    public SomeClass() throws Exception {
        throw new Exception("Constructor failed: Unable to create SomeClass object.");
    }
}

public class ConstructorExceptionDemo {

    public static void main(String[] args) {

        try {
            SomeClass obj = new SomeClass();

            System.out.println("Object created successfully.");
        }
        catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        System.out.println("Program continues after handling the exception.");
    }
}
