import java.util.Set;
import java.util.LinkedHashSet;
public class UsingHashSet{
	public static void main(String[] args){
		Set<String> emails = new LinkedHashSet<>();
		
		emails.add("john@example.com");
		emails.add("jane@example.com");
		emails.add("bob@example.com");
		emails.add("alice@example.com");
		emails.add("charlie@example.com");
		emails.add("david@example.com");
		emails.add("eve@example.com");
		emails.add("joe@example.com");
		emails.add("bmw@gmail.com");
		emails.add("benjohn@gmail.com");

		System.out.println(emails);
		for( String email : emails){
			System.out.println(email);
		}
		
	}
}