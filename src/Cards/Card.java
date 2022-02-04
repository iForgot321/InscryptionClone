package Cards;

public abstract class Card {
    public String name;
    public int bloodCost;
    public  int boneCost;
    public int power;
    public int health;

    public void printCard() {
        System.out.print("{" + name + ": " + power + "/" + health + "}");
    }

    public static Card enumToCard(CardEnum c) {
        Card result = null;
        switch (c) {
            case SQUIRREL:
                result = new Squirrel();
                break;
            case WOLF:
                result = new Wolf();
                break;
            case STUNTED_WOLF:
                result = new StuntedWolf();
                break;
            case COYOTE:
                result = new Coyote();
                break;
            case STOAT:
                result = new Stoat();
                break;
        }
        return result;
    }
}
