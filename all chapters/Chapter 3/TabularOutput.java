//3.22 
public class TabularOutput {
    public static void main(String[] args) {
        System.out.println("N\tN^2\tN^3");
        System.out.println("----------------");
        
        for (int i = 1; i <= 5; i++) {
            System.out.printf("%d\t%d\t%d%n", i, i * i, i * i * i);
        }
    }
}