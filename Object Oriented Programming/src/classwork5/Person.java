
package classwork5;

public abstract class Person {
    String name;
    int age;
    
    public Person (String name, int age){
        this.name = name;
        this.age = age;
    }  
        void displayDetails(){
        System.out.println("StudentsName": + name);
        System.out.println("StudentAge": + age);
        }
    abstract void performDuty();
    
}
