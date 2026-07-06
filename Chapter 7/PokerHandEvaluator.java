import java.util.*;

public class PokerHandEvaluator {

    public static boolean isPair(Card[] hand) {
        Map<Face, Integer> counts = getFaceCounts(hand);
        return counts.values().contains(2);
    }

    public static boolean isTwoPair(Card[] hand) {
        Map<Face, Integer> counts = getFaceCounts(hand);
        int pairs = 0;

        for (int v : counts.values()) {
            if (v == 2) pairs++;
        }
        return pairs == 2;
    }

    public static boolean isThreeOfKind(Card[] hand) {
        return getFaceCounts(hand).containsValue(3);
    }

    public static boolean isFourOfKind(Card[] hand) {
        return getFaceCounts(hand).containsValue(4);
    }

    public static boolean isFlush(Card[] hand) {
        Suit s = hand[0].getSuit();
        for (Card c : hand) {
            if (c.getSuit() != s) return false;
        }
        return true;
    }

    public static boolean isStraight(Card[] hand) {
        int[] values = new int[5];
        for (int i = 0; i < 5; i++) {
            values[i] = hand[i].getFace().ordinal();
        }

        Arrays.sort(values);

        for (int i = 1; i < values.length; i++) {
            if (values[i] != values[i - 1] + 1)
                return false;
        }
        return true;
    }

    public static boolean isFullHouse(Card[] hand) {
        Map<Face, Integer> counts = getFaceCounts(hand);
        boolean three = false, two = false;

        for (int v : counts.values()) {
            if (v == 3) three = true;
            if (v == 2) two = true;
        }
        return three && two;
    }

    private static Map<Face, Integer> getFaceCounts(Card[] hand) {
        Map<Face, Integer> map = new HashMap<>();

        for (Card c : hand) {
            map.put(c.getFace(),
                map.getOrDefault(c.getFace(), 0) + 1);
        }
        return map;
    }
}