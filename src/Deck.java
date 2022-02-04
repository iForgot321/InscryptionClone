import Cards.Card;
import Cards.CardEnum;
import Cards.Squirrel;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    public static int SQUIRREL_DECK_LENGTH = 10;

    public ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public Deck(CardEnum[] cards) {
        this.cards = new ArrayList<>();
        for (CardEnum card : cards) {
            this.cards.add(Card.enumToCard(card));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        Card draw = cards.get(cards.size() - 1);
        cards.remove(cards.size() - 1);
        return draw;
    }

    public static Deck getSquirrelDeck() {
        Deck squirrels = new Deck();
        for (int i = 0; i < SQUIRREL_DECK_LENGTH; i++) {
            squirrels.cards.add(new Squirrel());
        }
        return squirrels;
    }
}
