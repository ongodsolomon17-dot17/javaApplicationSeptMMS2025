import java.util.Random;

public class DeckOfCardsFisher-Yates {

    private Card[] deck;
    private int currentCard;

    private static final int SIZE = 52;
    private static final Random random = new Random();

    public DeckOfCardsFisher-Yates() {

        deck = new Card[SIZE];
        currentCard = 0;

        int index = 0;

        for (Suit suit : Suit.values()) {
            for (Face face : Face.values()) {
                deck[index++] = new Card(face, suit);
            }
        }
    }

    // Fisher–Yates shuffle
    public void shuffle() {

        currentCard = 0;

        for (int i = deck.length - 1; i > 0; i--) {

            int j = random.nextInt(i + 1);

            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }

    public Card dealCard() {
        return deck[currentCard++];
    }
}