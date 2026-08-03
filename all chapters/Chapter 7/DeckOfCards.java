import java.util.Random;

public class DeckOfCards {

    private Card[] deck;
    private int currentCard;
    private static final int SIZE = 52;
    private static final Random random = new Random();

    private static final String[] faces =
            {"Ace","Deuce","Three","Four","Five","Six",
             "Seven","Eight","Nine","Ten","Jack","Queen","King"};

    private static final String[] suits =
            {"Hearts","Diamonds","Clubs","Spades"};

    public DeckOfCards() {

        deck = new Card[SIZE];
        currentCard = 0;

        for (int i = 0; i < SIZE; i++) {
            deck[i] = new Card(faces[i % 13], suits[i / 13]);
        }
    }

    public void shuffle() {

        currentCard = 0;

        for (int i = 0; i < SIZE; i++) {
            int j = random.nextInt(SIZE);

            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }

    public Card dealCard() {
        return deck[currentCard++];
    }

    // ---------- HAND EVALUATION ----------

    public boolean isPair(Card[] hand) {
        return getMatches(hand) == 2;
    }

    public boolean isTwoPair(Card[] hand) {
        int count = 0;

        for (int i = 0; i < hand.length; i++) {
            for (int j = i + 1; j < hand.length; j++) {
                if (hand[i].getFace().equals(hand[j].getFace()))
                    count++;
            }
        }

        return count == 2;
    }

    public boolean isThreeOfKind(Card[] hand) {
        return getMaxSameFace(hand) == 3;
    }

    public boolean isFourOfKind(Card[] hand) {
        return getMaxSameFace(hand) == 4;
    }

    public boolean isFlush(Card[] hand) {

        String suit = hand[0].getSuit();

        for (Card c : hand)
            if (!c.getSuit().equals(suit))
                return false;

        return true;
    }

    public boolean isStraight(Card[] hand) {

        int[] values = getSortedValues(hand);

        for (int i = 0; i < values.length - 1; i++) {
            if (values[i] + 1 != values[i + 1])
                return false;
        }

        return true;
    }

    public boolean isFullHouse(Card[] hand) {

        return (getMatches(hand) == 2 && getMaxSameFace(hand) == 3);
    }

    // ---------- HELPERS ----------

    private int getMatches(Card[] hand) {

        int count = 0;

        for (int i = 0; i < hand.length; i++) {
            for (int j = i + 1; j < hand.length; j++) {
                if (hand[i].getFace().equals(hand[j].getFace()))
                    count++;
            }
        }

        return count;
    }

    private int getMaxSameFace(Card[] hand) {

        int max = 0;

        for (int i = 0; i < hand.length; i++) {

            int count = 1;

            for (int j = 0; j < hand.length; j++) {
                if (i != j &&
                    hand[i].getFace().equals(hand[j].getFace()))
                    count++;
            }

            if (count > max)
                max = count;
        }

        return max;
    }

    private int[] getSortedValues(Card[] hand) {

        int[] values = new int[5];

        for (int i = 0; i < hand.length; i++) {

            String face = hand[i].getFace();

            for (int j = 0; j < faces.length; j++) {
                if (faces[j].equals(face))
                    values[i] = j;
            }
        }

        java.util.Arrays.sort(values);

        return values;
    }
}