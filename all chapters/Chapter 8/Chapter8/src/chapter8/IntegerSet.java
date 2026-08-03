
package chapter8;

public class IntegerSet {
    // Array size is 101 to accommodate numbers from 0 to 100 inclusive
    private static final int SET_SIZE = 101;
    private final boolean[] set;

    // No-argument constructor: initializes an empty set (all elements default to false)
    public IntegerSet() {
        set = new boolean[SET_SIZE];
    }

    // Static method: creates the union of two IntegerSets (A ∪ B)
    public static IntegerSet union(IntegerSet set1, IntegerSet set2) {
        IntegerSet resultSet = new IntegerSet();
        for (int i = 0; i < SET_SIZE; i++) {
            resultSet.set[i] = set1.set[i] || set2.set[i];
        }
        return resultSet;
    }

    // Static method: creates the intersection of two IntegerSets (A ∩ B)
    public static IntegerSet intersection(IntegerSet set1, IntegerSet set2) {
        IntegerSet resultSet = new IntegerSet();
        for (int i = 0; i < SET_SIZE; i++) {
            resultSet.set[i] = set1.set[i] && set2.set[i];
        }
        return resultSet;
    }

    // Inserts integer k into the set (sets set[k] to true)
    public void insertElement(int k) {
        if (k >= 0 && k < SET_SIZE) {
            set[k] = true;
        } else {
            throw new IllegalArgumentException("Element must be in the range 0-100.");
        }
    }

    // Deletes integer m from the set (sets set[m] to false)
    public void deleteElement(int m) {
        if (m >= 0 && m < SET_SIZE) {
            set[m] = false;
        } else {
            throw new IllegalArgumentException("Element must be in the range 0-100.");
        }
    }

    // Checks whether two IntegerSets contain the exact same elements
    public boolean isEqualTo(IntegerSet otherSet) {
        for (int i = 0; i < SET_SIZE; i++) {
            if (this.set[i] != otherSet.set[i]) {
                return false;
            }
        }
        return true;
    }

    // Returns a space-separated String of elements present in the set, or "---" if empty
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        boolean isEmpty = true;

        for (int i = 0; i < SET_SIZE; i++) {
            if (set[i]) {
                builder.append(i).append(" ");
                isEmpty = false;
            }
        }

        return isEmpty ? "---" : builder.toString().trim();
    }
}
