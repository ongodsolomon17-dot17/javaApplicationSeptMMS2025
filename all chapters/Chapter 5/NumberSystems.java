public class NumberSystems {

    public static void main(String[] args) {

        System.out.println("Decimal\tBinary\t\tOctal\tHex");

        for (int i = 1; i <= 256; i++) {

            System.out.printf("%d\t%s\t\t%s\t%s%n",
                    i,
                    Integer.toBinaryString(i),
                    Integer.toOctalString(i),
                    Integer.toHexString(i).toUpperCase());
        }
    }
}