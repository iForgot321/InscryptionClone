import Cards.CardEnum;

public class Inscryption {
    public static void main(String[] args) {
        CardEnum[] deck = {
                CardEnum.SQUIRREL,
                CardEnum.WOLF,
                CardEnum.STOAT,
                CardEnum.STUNTED_WOLF,
                CardEnum.COYOTE
        };
        Game game = new Game(deck);
        game.start();
    }
}
