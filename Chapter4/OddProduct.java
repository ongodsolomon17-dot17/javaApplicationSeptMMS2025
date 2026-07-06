public class OddProduct {
   public static void main(String[] args) {
      int product = 1;
      for (int i = 1; i <= 15; i += 2) {
         product = product * i;
      }
      System.out.println("Product of odd ints from 1 to 15 is " + product);
   }
}
