public class PokerTest {

    public static void main(String[] args) {

        DeckOfCards deck = new DeckOfCards();

        deck.shuffle();

        Card[] hand = new Card[5];

        for (int i = 0; i < 5; i++) {
            hand[i] = deck.dealCard();
        }

        System.out.println("Your Hand:");

        for (Card c : hand)
            System.out.println(c);

        System.out.println("\nResults:");

        if (deck.isStraight(hand)) System.out.println("Straight");
        if (deck.isFlush(hand)) System.out.println("Flush");
        if (deck.isFullHouse(hand)) System.out.println("Full House");
        if (deck.isFourOfKind(hand)) System.out.println("Four of a Kind");
        if (deck.isThreeOfKind(hand)) System.out.println("Three of a Kind");
        if (deck.isTwoPair(hand)) System.out.println("Two Pair");
        if (deck.isPair(hand)) System.out.println("Pair");
    }
}