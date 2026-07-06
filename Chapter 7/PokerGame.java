public class PokerGame {

    public static void main(String[] args) {

        DeckOfCards deck = new DeckOfCards();
        deck.shuffle();

        Card[] hand1 = dealHand(deck);
        Card[] hand2 = dealHand(deck);

        System.out.println("Hand 1:");
        printHand(hand1);

        System.out.println("\nHand 2:");
        printHand(hand2);

        System.out.println("\nResult:");
        System.out.println(compareHands(hand1, hand2));
    }

    private static Card[] dealHand(DeckOfCards deck) {
        Card[] hand = new Card[5];
        for (int i = 0; i < 5; i++) {
            hand[i] = deck.dealCard();
        }
        return hand;
    }

    private static void printHand(Card[] hand) {
        for (Card c : hand) {
            System.out.println(c);
        }
    }

    private static String compareHands(Card[] h1, Card[] h2) {

        int r1 = rank(h1);
        int r2 = rank(h2);

        if (r1 > r2) return "Hand 1 wins";
        if (r2 > r1) return "Hand 2 wins";
        return "Tie";
    }

    // simple ranking system
    private static int rank(Card[] h) {
        if (PokerHandEvaluator.isStraight(h) && PokerHandEvaluator.isFlush(h)) return 8;
        if (PokerHandEvaluator.isFourOfKind(h)) return 7;
        if (PokerHandEvaluator.isFullHouse(h)) return 6;
        if (PokerHandEvaluator.isFlush(h)) return 5;
        if (PokerHandEvaluator.isStraight(h)) return 4;
        if (PokerHandEvaluator.isThreeOfKind(h)) return 3;
        if (PokerHandEvaluator.isTwoPair(h)) return 2;
        if (PokerHandEvaluator.isPair(h)) return 1;
        return 0;
    }
}