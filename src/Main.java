import java.util.Scanner;
public class Main {
    static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        GameLogic gameLogic = new GameLogic();
        char playerSelection;


        while (gameLogic.getGamePlayed() <= gameLogic.getTotalRounds()) {
            do {
                System.out.print("Choose: Rock (R/r), Paper (P/p), Scissor (S/s) => ");
                playerSelection = scnr.next().toUpperCase().charAt(0);

                if (gameLogic.isValid(playerSelection)) {
                    System.out.println("\nInvalid Selection: Select Again");
                }
            } while (gameLogic.isValid(playerSelection));

            gameLogic.checkGame(playerSelection);


            if (gameLogic.getGamePlayed() > gameLogic.getTotalRounds()) {
                gameLogic.endGameStats();
            }
        }
    }

}