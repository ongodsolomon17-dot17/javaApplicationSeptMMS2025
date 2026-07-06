public class Dealer {

    public static void improveHand(Card[] hand, DeckOfCards deck) {

        if (PokerHandEvaluator.isPair(hand)) return;

        // naive: replace 3 cards
        for (int i = 0; i < 3; i++) {
            hand[i] = deck.dealCard();
        }
    }
}