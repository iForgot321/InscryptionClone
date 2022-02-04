import Cards.Card;
import Cards.CardEnum;
import Cards.Stoat;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public Card[][] board;
    public Player player;
    public int scale;

    public Game(CardEnum[] deck) {
        this.board = new Card[3][4];
        this.player = new Player(deck);
        this.scale = 0;
    }

    public void start() {
        initBoard();
        player.initHand();
        Scanner scan = new Scanner(System.in);
        int turn = 0;
        while (scale < 5 && scale > -5) {
            printState();

            if (turn % 2 == 0) { // Human turn
                if (turn != 0) {
                    startDrawPhase(scan);
                }
                player.printHand();
                startActionPhase(scan);
            } else { // Computer turn
                enemyAdvance();
                enemySpawn();
            }

            startCombatPhase(turn % 2 == 0);

            this.player.blood = 0;
            turn++;
        }
        endGame();
    }

    public void initBoard() {
        Random rand = new Random();
        int index = rand.nextInt(board[0].length);
        board[0][index] = new Stoat();
    }

    public void startDrawPhase(Scanner scan) {
        System.out.println("Choose which deck to draw (0 for main, 1 for sac)");
        int choice = scan.nextInt();
        if (choice == 0) {
            player.drawMainDeck();
        } else {
            player.drawSacDeck();
        }
        System.out.println();
    }

    public void startActionPhase(Scanner scan) {
        while (true) {
            System.out.print("Choose type of action (0 to ring bell, 1 to play card, 2 to sac card, 3 to use item): ");
            int action = scan.nextInt();
            if (action == 0) {
                System.out.println("Passing turn");
                break;
            } else if (action == 1) {
                actionPlayCard(scan);
            } else if (action == 2) {
                actionSacCard(scan);
            } else if (action == 3) {
                System.out.println("Feature not implemented");
            }
            System.out.println();
            printState();
            player.printHand();
        }
    }

    public void enemyAdvance() {
        for (int i = 0; i < board[0].length; i++) {
            if (board[0][i] != null && board[1][i] == null) {
                Card enemy = board[0][i];
                board[1][i] = enemy;
                board[0][i] = null;
            }
        }
    }

    public void enemySpawn() {
        Random rand = new Random();
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < board[0].length; i++) {
            if (board[0][i] == null) {
                indices.add(i);
            }
        }

        if (indices.isEmpty()) {
            return;
        }

        int index = rand.nextInt(indices.size());
        board[0][indices.get(index)] = new Stoat();
    }

    public void startCombatPhase(boolean isAttackingNorth) {
        int row = isAttackingNorth ? 2 : 1;
        int dir = isAttackingNorth ? -1 : 1;
        for (int i = 0; i < board[0].length; i++) {
            if (board[row][i] != null) {
                Card attacker = board[row][i];
                if (board[row + dir][i] == null) {
                    scale = scale + -dir * attacker.power;
                } else {
                    Card defender = board[row + dir][i];
                    defender.health -= attacker.power;
                    if (defender.health <= 0) {
                        if (!isAttackingNorth) {
                            player.bones++;
                        }
                        board[row + dir][i] = null;
                    }
                }
            }
        }
    }

    public void actionPlayCard(Scanner scan) {
        System.out.print("Choose card to play: ");
        int handIndex = scan.nextInt();
        if (handIndex >= player.hand.size()) {
            System.out.println("Invalid choice\n\n");
            return;
        }
        Card chosen = player.hand.get(handIndex);
        if (player.blood < chosen.bloodCost || player.bones < chosen.boneCost) {
            System.out.println("Not enough blood/bones");
            return;
        }

        System.out.print("You chose ");
        chosen.printCard();
        System.out.println();

        System.out.print("Choose where to play: ");
        int boardIndex = scan.nextInt();
        if (boardIndex > 3 || boardIndex < 0 || board[2][boardIndex] != null) {
            System.out.println("Invalid choice\n\n");
            return;
        }
        board[2][boardIndex] = chosen;
        player.blood -= chosen.bloodCost;
        player.bones -= chosen.boneCost;
        player.hand.remove(handIndex);
    }

    public void actionSacCard(Scanner scan) {
        System.out.print("Choose where to sac: ");
        int boardIndex = scan.nextInt();
        if (boardIndex > 3 || boardIndex < 0 || board[2][boardIndex] == null) {
            System.out.println("Invalid choice\n\n");
            return;
        }
        board[2][boardIndex] = null;
        player.blood++;
        player.bones++;
    }

    public void endGame() {
        printState();
        if (scale < 0) {
            System.out.println("You lost");
        } else {
            System.out.println("You won");
        }
    }

    public void printState() {
        System.out.println("The scale is " + scale);
        player.printBlood();
        player.printBones();
        printBoard();
    }

    public void printBoard() {
        System.out.println("The board: ");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == null) {
                    System.out.print("{           }");
                } else {
                    board[i][j].printCard();
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
