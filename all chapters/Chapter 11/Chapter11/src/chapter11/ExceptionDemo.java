package chapter11;

import java.io.IOException;

class ExceptionA extends Exception {
    public ExceptionA(String message) {
        super(message);
    }
}

class ExceptionB extends ExceptionA {
    public ExceptionB(String message) {
        super(message);
    }
}

public class ExceptionDemo {

    public static void throwExceptionA() throws ExceptionA {
        throw new ExceptionA("This is ExceptionA");
    }

    public static void throwExceptionB() throws ExceptionB {
        throw new ExceptionB("This is ExceptionB");
    }

    public static void throwIOException() throws IOException {
        throw new IOException("This is an IOException");
    }

    public static void main(String[] args) {

   
        try {
            throwExceptionA();
        } catch (Exception exception) {
            System.out.println("Caught: " + exception);
        }

        try {
            throwExceptionB();
        } catch (Exception exception) {
            System.out.println("Caught: " + exception);
        }

        try {
            String str = null;
            System.out.println(str.length());
        } catch (Exception exception) {
            System.out.println("Caught: " + exception);
        }

        try {
            throwIOException();
        } catch (Exception exception) {
            System.out.println("Caught: " + exception);
        }
    }
}
