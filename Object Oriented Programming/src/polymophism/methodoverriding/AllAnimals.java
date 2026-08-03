
package polymophism.methodoverriding;

public class AllAnimals  {
    public static void main(String[] args){
        Animal[] animalObject = {
            new Dog(),
            new Cat(),
            new Lion(),
        };
        
        
        for(Animal animal : animalObject){
            animal.sound();
        }
        
      
        
        
    
    }
    
}
