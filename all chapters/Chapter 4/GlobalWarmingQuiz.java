import java.util.Scanner;

public class GlobalWarmingQuiz {
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      int score = 0;

      System.out.println("Global Warming Quiz");
      System.out.println();

      System.out.println("1. What is the most abundant greenhouse gas in Earth's atmosphere?");
      System.out.println("1) Carbon Dioxide  2) Water Vapor  3) Methane  4) Nitrous Oxide");
      System.out.print("Answer: ");
      if (input.nextInt() == 2) score++;

      System.out.println();
      System.out.println("2. The Intergovernmental Panel on Climate Change (IPCC) was awarded the");
      System.out.println("   Nobel Peace Prize in what year?");
      System.out.println("1) 2005  2) 2006  3) 2007  4) 2008");
      System.out.print("Answer: ");
      if (input.nextInt() == 3) score++;

      System.out.println();
      System.out.println("3. Some scientists who question global warming are sometimes called:");
      System.out.println("1) Believers  2) Skeptics  3) Deniers  4) Activists");
      System.out.print("Answer: ");
      if (input.nextInt() == 2) score++;

      System.out.println();
      System.out.println("4. What is the name of the 2006 documentary featuring Al Gore?");
      System.out.println("1) The Day After Tomorrow  2) An Inconvenient Truth");
      System.out.println("3) Earth  4) Climate Change 101");
      System.out.print("Answer: ");
      if (input.nextInt() == 2) score++;

      System.out.println();
      System.out.println("5. Some skeptics argue that climate change is caused mostly by:");
      System.out.println("1) Solar activity  2) Pollution  3) CFCs  4) Volcanoes");
      System.out.print("Answer: ");
      if (input.nextInt() == 1) score++;

      System.out.println();
      System.out.println("You got " + score + " out of 5 correct.");

      if (score == 5)
         System.out.println("Excellent!");
      else if (score == 4)
         System.out.println("Very good!");
      else {
         System.out.println("Time to brush up on your knowledge of global warming.");
         System.out.println("Check out these sites:");
         System.out.println("https://www.ipcc.ch/");
         System.out.println("https://climate.nasa.gov/");
         System.out.println("https://en.wikipedia.org/wiki/Global_warming");
      }
   }
}
