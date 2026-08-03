package chapter10;

public class ShapeHierarchyEvaluator {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[2]; 
        shapes[0] = new Circle(4.5);
        shapes[1] = new Sphere(3.0);

        System.out.println("--- Shape Hierarchy Analysis ---\n");

        for (Shape shape : shapes) { 
            System.out.println(shape.toString()); 

            if (shape instanceof TwoDimensionalShape twoD) { 
                System.out.printf("Shape Type: Two-Dimensional%nArea: %.2f%n%n", twoD.getArea()); 
            } else if (shape instanceof ThreeDimensionalShape threeD) { 
                System.out.printf("Shape Type: Three-Dimensional%nSurface Area: %.2f%nVolume: %.2f%n%n", 
                    threeD.getArea(), threeD.getVolume()); 
            }
        }
    }
}