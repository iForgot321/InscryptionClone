import Cards.Card;
import Cards.CardEnum;

import java.util.ArrayList;

public class Player {
    public static int STARTING_HAND_LENGTH = 3;

    public Deck mainDeck;
    public Deck sacDeck;
    public ArrayList<Card> hand;
    public int blood;
    public int bones;

    public Player(CardEnum[] mainDeck) {
        this.mainDeck = new Deck(mainDeck);
        this.mainDeck.shuffle();
        this.sacDeck = Deck.getSquirrelDeck();
        this.hand = new ArrayList<>();
        this.bones = 0;
        this.blood = 0;
    }

    public void initHand() {
        drawSacDeck();
        for (int i = 0; i < STARTING_HAND_LENGTH; i++) {
            drawMainDeck();
        }
    }

    public void drawSacDeck() {
        hand.add(sacDeck.drawCard());
    }

    public void drawMainDeck() {
        hand.add(mainDeck.drawCard());
    }

    public void printHand() {
        System.out.println("Your hand: ");
        for (Card c : hand) {
            c.printCard();
            System.out.print(" ");
        }
        System.out.println();
    }

    public void printBlood() {
        System.out.println("Blood count: " + this.blood);
    }

    public void printBones() {
        System.out.println("Bone count: " + this.bones);
    }
}
