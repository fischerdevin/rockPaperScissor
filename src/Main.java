import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        GameLogic gameLogic = new GameLogic();
        Computer computer = new Computer();


        Testing testing = new Testing();
        testing.testEveryPossibleValid();


        char playerSelectionChar;
        GameLogic.Selection playerSelection, computerSelection;

        while (gameLogic.getGamesPlayed() <= gameLogic.getTotalRounds()) {

            do {
                System.out.print("Choose: Rock (R/r), Paper (P/p), Scissor (S/s) => ");
                playerSelectionChar = scnr.next().toUpperCase().charAt(0);

                if (!isValidSelection(playerSelectionChar)) {
                    System.out.println("\nInvalid Selection: Select Again");
                }

            } while (!isValidSelection(playerSelectionChar));

            playerSelection = charToSelection(playerSelectionChar);
            computerSelection = computer.computerSelection();
            gameLogic.playRound(playerSelection, computerSelection);

        }

        gameLogic.printEndGameStats();
    }

    public static GameLogic.Selection charToSelection(char input) {

        assert(isValidSelection(input));

        switch (Character.toUpperCase(input)) {

            case 'R':
                return GameLogic.Selection.ROCK;
            case 'P':
                return GameLogic.Selection.PAPER;
            case 'S':
                return GameLogic.Selection.SCISSORS;
            default: 
                throw new IllegalArgumentException("Invalid selection: " + input);
        }

    }

    public static boolean isValidSelection(char input) {
        return input == 'R' || input == 'P' || input == 'S'; 
    }

}