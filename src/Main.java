import java.util.Scanner;
import java.util.Random;
public class Main {
    static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        Random random = new Random();

        int gamePlayed = 1, totalRounds = 20, playerWins = 0, computerWins = 0, drawCounter = 0;
        char playerSelection, computerSelection;
        final String validSelection = "RPS";


        while (gamePlayed <= totalRounds) {

            //Gate for only allowing R,r,S,s,P,p
            do {
                System.out.print("Choose: Rock (R/r), Paper (P/p), Scissor (S/s) => ");
                playerSelection = scnr.next().toUpperCase().charAt(0);

                if (validSelection.indexOf(playerSelection) == -1) {
                    System.out.println("\nInvalid Selection: Select Again");
                }
            } while (validSelection.indexOf(playerSelection) == -1);

            // Computers Random Choice
            computerSelection = validSelection.charAt(random.nextInt(validSelection.length()));

            System.out.println("Game: " + gamePlayed + " | Player choice : Computer Choice => " + playerSelection + " : " + computerSelection);

            switch (checkGame(playerSelection, computerSelection)) {
                case 0: {
                    drawCounter++;
                    break;
                }
                case 1: {
                    playerWins++;
                    break;
                }
                case 2: {
                    computerWins++;
                    break;
                }
                default: {
                    System.out.println("Error");
                    break;
                }
            }

            if (gamePlayed == totalRounds) {
                System.out.println("\nEnd of the Rounds Stats: ");
                System.out.println("Total games played: " + gamePlayed + " | Results (Win ,Loss ,Draw ): (" + playerWins + ", " + computerWins + ", " + drawCounter + ")\n");

            } else {
                System.out.println("Game " + gamePlayed + " | Results (Win ,Loss ,Draw ): (" + playerWins + ", " + computerWins + ", " + drawCounter + ")\n");
            }
            gamePlayed++;
        }
    }

    public static int checkGame(char player, char computer) {
        final char ROCK = 'R', PAPER = 'P', SCISSOR = 'S';
        if (player == computer) {
            System.out.println("Draw!");
            return 0;
        } else if (player == PAPER && computer == ROCK ||
                player == ROCK && computer == SCISSOR ||
                player == SCISSOR && computer == PAPER) {
            System.out.println("Player Wins!");
            return 1;
        } else {
            System.out.println("Computer Wins!");
            return 2;
        }
    }
}