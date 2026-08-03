
package chapter8;

public class HugeInteger {
    private static final int NUM_DIGITS = 40;
    private final int[] digits = new int[NUM_DIGITS]; // Stores digits, right-aligned

    // Default constructor: initializes to 0
    public HugeInteger() {
        // Elements default to 0 in Java
    }

    // Constructor that parses a String
    public HugeInteger(String val) {
        parse(val);
    }

    // Parses a String of digits and places them into the array (right-aligned)
    public void parse(String val) {
        // Reset array
        for (int i = 0; i < NUM_DIGITS; i++) {
            digits[i] = 0;
        }

        val = val.trim();
        if (val.length() > NUM_DIGITS) {
            throw new IllegalArgumentException("String exceeds max limit of 40 digits.");
        }

        int offset = NUM_DIGITS - val.length();
        for (int i = 0; i < val.length(); i++) {
            char ch = val.charAt(i);
            if (!Character.isDigit(ch)) {
                throw new IllegalArgumentException("Invalid digit character: " + ch);
            }
            digits[offset + i] = Character.getNumericValue(ch);
        }
    }

    // Adds two HugeInteger objects (this + operand)
    public HugeInteger add(HugeInteger operand) {
        HugeInteger result = new HugeInteger();
        int carry = 0;

        for (int i = NUM_DIGITS - 1; i >= 0; i--) {
            int sum = this.digits[i] + operand.digits[i] + carry;
            result.digits[i] = sum % 10;
            carry = sum / 10;
        }

        if (carry > 0) {
            throw new ArithmeticException("Overflow: Sum exceeds 40 digits capacity.");
        }

        return result;
    }

    // Subtracts operand from this (assumes this >= operand)
    public HugeInteger subtract(HugeInteger operand) {
        if (this.isLessThan(operand)) {
            throw new ArithmeticException("Negative result not supported: left operand must be >= right operand.");
        }

        HugeInteger result = new HugeInteger();
        int borrow = 0;

        for (int i = NUM_DIGITS - 1; i >= 0; i--) {
            int diff = this.digits[i] - operand.digits[i] - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result.digits[i] = diff;
        }

        return result;
    }

    // Multiplies two HugeInteger objects (this * operand)
    public HugeInteger multiply(HugeInteger operand) {
        HugeInteger result = new HugeInteger();

        for (int i = NUM_DIGITS - 1; i >= 0; i--) {
            int digit2 = operand.digits[i];
            if (digit2 == 0) continue;

            int carry = 0;
            int shift = (NUM_DIGITS - 1) - i; // Position shift for place value

            for (int j = NUM_DIGITS - 1; j >= 0; j--) {
                int targetIdx = j - shift;
                if (targetIdx < 0) {
                    if (this.digits[j] > 0 || carry > 0) {
                        throw new ArithmeticException("Overflow: Product exceeds 40 digits.");
                    }
                    continue;
                }

                int prod = result.digits[targetIdx] + (this.digits[j] * digit2) + carry;
                result.digits[targetIdx] = prod % 10;
                carry = prod / 10;
            }
        }

        return result;
    }

    // --- Predicate Comparison Methods ---

    public boolean isEqualTo(HugeInteger operand) {
        for (int i = 0; i < NUM_DIGITS; i++) {
            if (this.digits[i] != operand.digits[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean isNotEqualTo(HugeInteger operand) {
        return !isEqualTo(operand);
    }

    public boolean isGreaterThan(HugeInteger operand) {
        for (int i = 0; i < NUM_DIGITS; i++) {
            if (this.digits[i] > operand.digits[i]) return true;
            if (this.digits[i] < operand.digits[i]) return false;
        }
        return false;
    }

    public boolean isLessThan(HugeInteger operand) {
        for (int i = 0; i < NUM_DIGITS; i++) {
            if (this.digits[i] < operand.digits[i]) return true;
            if (this.digits[i] > operand.digits[i]) return false;
        }
        return false;
    }

    public boolean isGreaterThanOrEqualTo(HugeInteger operand) {
        return isGreaterThan(operand) || isEqualTo(operand);
    }

    public boolean isLessThanOrEqualTo(HugeInteger operand) {
        return isLessThan(operand) || isEqualTo(operand);
    }

    public boolean isZero() {
        for (int i = 0; i < NUM_DIGITS; i++) {
            if (digits[i] != 0) return false;
        }
        return true;
    }

    // Returns string without leading zeros (unless value is 0)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int startIdx = 0;

        // Skip leading zeros
        while (startIdx < NUM_DIGITS - 1 && digits[startIdx] == 0) {
            startIdx++;
        }

        for (int i = startIdx; i < NUM_DIGITS; i++) {
            sb.append(digits[i]);
        }

        return sb.toString();
    }
}


