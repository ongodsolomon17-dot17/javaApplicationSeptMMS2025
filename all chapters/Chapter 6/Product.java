public class Product {

    public static int product(int... numbers) {

        int result = 1;

        for (int value : numbers)
            result *= value;

        return result;
    }

    public static void main(String[] args) {

        System.out.println(product(2,3));
        System.out.println(product(2,3,4));
        System.out.println(product(2,3,4,5));
        System.out.println(product(5,6,7,8,9));
    }
}