
package classwork5;

public class Students extends Person {
    

    public Students(String name, int age) {
        super(name, age);
    }

    @Override
    void performDuty() {
        System.out.println("STudnet's duty is to study");
    }
    
}
