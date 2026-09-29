import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        GameLogic gameLogic = new GameLogic();
        Computer computer = new Computer();


        Testing testing = new Testing();
//        testing.testEveryPossibleValid();


        char playerSelection,computerSelection;


        while (gameLogic.getGamesPlayed() <= gameLogic.getTotalRounds()) {
            do {
                System.out.print("Choose: Rock (R/r), Paper (P/p), Scissor (S/s) => ");
                playerSelection = scnr.next().toUpperCase().charAt(0);

                if (!gameLogic.isValid(playerSelection)) {
                    System.out.println("\nInvalid Selection: Select Again");
                }
            } while (!gameLogic.isValid(playerSelection));

            computerSelection = computer.computerSelection();
            GameLogic.Result result = gameLogic.checkGame(playerSelection,computerSelection);
            System.out.println("Game: " + gameLogic.getGamesPlayed() + " | Player choice : Computer Choice => " + playerSelection + " : " + computerSelection);


            gameLogic.playGame(result);


            if (gameLogic.getGamesPlayed() > gameLogic.getTotalRounds()) {
                gameLogic.endGameStats();
            }
        }
    }

}