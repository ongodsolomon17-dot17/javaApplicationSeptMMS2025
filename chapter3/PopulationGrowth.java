//3.39 
public class PopulationGrowth {
    public static void main(String[] args) {
        long currentPopulation = 8000000000L; // Estimated starting population
        double growthRate = 0.01;            // Estimated current annual growth rate (1%)
        long doublePopulationYear = 0;
        
        System.out.printf("%-10s%-20s%-20s%n", "Year", "Anticipated Population", "Numerical Increase");
        System.out.println("---------------------------------------------------------------");

        for (int year = 1; year <= 75; year++) {
            long increase = (long) (currentPopulation * growthRate);
            long anticipatedPopulation = currentPopulation + increase;
            
            System.out.printf("%-10d%-20d%-20d%n", year, anticipatedPopulation, increase);
            
            // Check when population doubles
            if (anticipatedPopulation >= currentPopulation * 2 && doublePopulationYear == 0) {
                doublePopulationYear = year;
            }
            
            currentPopulation = anticipatedPopulation;
        }

        System.out.println("\nIf this year's growth rate persists, the population would double in Year: " + doublePopulationYear);
    }
}