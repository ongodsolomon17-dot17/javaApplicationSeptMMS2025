
package classwork3;

public class Person {
    void display(){
    System.out.println("No paremeter was given");
    }
    void display(String name){
        System.out.println("Your name is" + name);
    }
    
    void display(String name, int age){
        System.out.println("you're" + age + "years old");

    }
    
}
