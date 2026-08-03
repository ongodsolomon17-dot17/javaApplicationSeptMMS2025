package chapter11;

public class TestExceptions {

    public static void throwB() throws ExceptionB {
        throw new ExceptionB("ExceptionB occurred");
    }

    public static void throwC() throws ExceptionC {
        throw new ExceptionC("ExceptionC occurred");
    }

    public static void main(String[] args) {

        try {
            throwB();
        } catch (ExceptionA e) {
            System.out.println("Caught by ExceptionA: " + e.getMessage());
        }

        try {
            throwC();
        } catch (ExceptionA e) {
            System.out.println("Caught by ExceptionA: " + e.getMessage());
        }
    }
}
