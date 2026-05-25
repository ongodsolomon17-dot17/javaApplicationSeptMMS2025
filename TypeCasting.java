public class TypeCasting{
	public static void main(String[] arg){
		double price = 7658;
		System.out.printf("The price of laptop is %f%n", price);
		int num1 = 50;
		int num2 = 200;
		
		double division = (double)num1/num2;
		System.out.printf("The division is %f%n",division );
		
		double balance = 5693.875;
		int convertedBalance = (int)balance;
		System.out.printf("The convertedBalance is %d%n",convertedBalance);
		
		char symbol = '?';
		int convertedSymbol = (int)symbol;
		System.out.printf("The convertedSymbol is %d%n",convertedSymbol);
	}
}