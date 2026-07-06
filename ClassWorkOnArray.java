public class ClassWorkOnArray7 {
    public static void main(String[] args) {

        int[] displayEven = {7, 8, 4, 3, 2, 90, 1, 6, 8};

        for (int i = 0; i < displayEven.length; i++) {
            if (displayEven[i] % 2 == 0) {
                System.out.println(displayEven[i]);
            }
        }
    }
}