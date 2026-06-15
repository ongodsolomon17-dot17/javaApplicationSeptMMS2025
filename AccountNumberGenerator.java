import java.util.Random;

public class AccountNumberGenerator{
	public static void main(String[] args){
		Random random = new Random();
		String account = "000";
		
		for(int i = 0; i < 7; i++){
			account = account + random.nextInt(10);
		}
		
		System.out.println("Account Number: " + account);
	}
}