
package abstraction;


public class Person {
    String firstNmae;
    String lastName;
    String phoneNumber;
    String address;
    
    void eat(){
    System.out.println("The person is eating");
    }
    abstract void sing();
}
