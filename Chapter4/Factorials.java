public class Factorials {
   public static void main(String[] args) {
      System.out.println("Number\tFactorial");
      long factorial = 1;
      for (int n = 1; n <= 20; n++) {
         factorial = factorial * n;
         System.out.println(n + "\t" + factorial);
      }
   }
}
