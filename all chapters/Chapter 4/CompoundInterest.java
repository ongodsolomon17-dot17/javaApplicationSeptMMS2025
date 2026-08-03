public class CompoundInterest {
   public static void main(String[] args) {
      double principal = 1000.0;

      System.out.println("Rate\tYear\tAmount");
      for (int rate = 5; rate <= 10; rate++) {
         for (int year = 1; year <= 10; year++) {
            double amount = principal * Math.pow(1.0 + rate / 100.0, year);
            System.out.println(rate + "%\t" + year + "\t" +
               String.format("%.2f", amount));
         }
         System.out.println();
      }
   }
}
