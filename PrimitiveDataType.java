public class PrimitiveDataType{
	public static void main(String[] args){
		byte myAge = 30;
		System.out.printf("miss Mercy is %d years old%n",myAge);
		
		short quantity = 20000;
		System.out.printf("The quantity of iphones ordered is %,d%n",quantity);
		
		int nigeriaPopulation = 294848848;
		System.out.printf("This population of Nigeria is %,d%n",nigeriaPopulation);

		long worldPopulation = 47474778478474874L;
		System.out.printf("This world's population is %,d%n",worldPopulation);
		
		float price = 577858.84747F;
		System.out.printf("The price of each iphone per unit is %,.2f%n",price); 
		
		double myBalance = 7464784747747474.8474747;
		System.out.printf("My account balance is %c%,.2f%n",'$',myBalance);
		
		char symbol = '%';
		System.out.printf("ther is a increment in the world's populationby 20.5%c%n",symbol);
		
		boolean isJavaFun = true;
		System.out.printf("Do you love java? %b",isJavaFun);
	}	
}