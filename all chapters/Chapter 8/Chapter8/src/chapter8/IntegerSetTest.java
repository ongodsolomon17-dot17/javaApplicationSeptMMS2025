
package chapter8;

public class IntegerSetTest {
    public static void main(String[] args) {
        // Instantiate two integer sets
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        System.out.println("=== Initial State ===");
        System.out.println("Set 1 (empty): " + set1);
        System.out.println("Set 2 (empty): " + set2);

        // Populate Set 1: {10, 25, 50, 75, 100}
        set1.insertElement(10);
        set1.insertElement(25);
        set1.insertElement(50);
        set1.insertElement(75);
        set1.insertElement(100);

        // Populate Set 2: {25, 50, 60, 80}
        set2.insertElement(25);
        set2.insertElement(50);
        set2.insertElement(60);
        set2.insertElement(80);

        System.out.println("\n=== After Adding Elements ===");
        System.out.println("Set 1: " + set1);
        System.out.println("Set 2: " + set2);

        // Test union
        IntegerSet unionSet = IntegerSet.union(set1, set2);
        System.out.println("\n=== Set Operations ===");
        System.out.println("Union (Set 1 ∪ Set 2): " + unionSet);

        // Test intersection
        IntegerSet intersectionSet = IntegerSet.intersection(set1, set2);
        System.out.println("Intersection (Set 1 ∩ Set 2): " + intersectionSet);

        // Test deleteElement
        System.out.println("\n=== Testing Deletion ===");
        set1.deleteElement(100);
        System.out.println("Set 1 after deleting 100: " + set1);

        // Test isEqualTo
        System.out.println("\n=== Testing Equality ===");
        System.out.println("Is Set 1 equal to Set 2? " + set1.isEqualTo(set2));

        IntegerSet set3 = new IntegerSet();
        set3.insertElement(10);
        set3.insertElement(25);
        set3.insertElement(50);
        set3.insertElement(75);

        System.out.println("Set 3: " + set3);
        System.out.println("Is Set 1 equal to Set 3? " + set1.isEqualTo(set3));
    }
}
