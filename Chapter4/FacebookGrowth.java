public class FacebookGrowth {
   public static void main(String[] args) {
      double users = 1000000000;
      double target1 = 1500000000;
      double target2 = 2000000000;
      int months1 = 0;
      int months2 = 0;
      double rate = 0.04;

      while (users < target1) {
         users = users * (1 + rate);
         months1++;
      }
      System.out.println("Months to reach 1.5 billion: " + months1);

      while (users < target2) {
         users = users * (1 + rate);
         months2++;
      }
      System.out.println("Months to reach 2 billion: " + (months1 + months2));
   }
}
