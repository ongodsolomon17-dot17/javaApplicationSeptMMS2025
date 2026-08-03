
package classwork;

public class AllShapes {
    public static void main(String[] args){
    
        Shape[] shapeObject = {
        new Circle(),
        new Triangle(),
        new Rectangle(),
                
        };
        
        for(Shape shape : shapeObject){
        shape.draw();
        }
    }
    
}
