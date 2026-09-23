import java.util.Scanner;
import java.util.Random;
public class Main {
    public static void main(String[] args){
        Scanner scnr = new Scanner(System.in);
        int gamePlayed = 1, rounds = 20, playerWins = 0, computerWins = 0, drawCounter =0;
        char playerSelection, computerSelection;
        Random random = new Random();
        final char ROCK = 'R', PAPER = 'P', SCISSOR = 'S';
        final char[] playableSelection = {ROCK,PAPER,SCISSOR};
        final String validSelections = new String(playableSelection);

        while (gamePlayed <= rounds) {

            //Gate for only allowing R,r,S,s,P,p
            do {
                System.out.print("Choose: Rock (R/r), Paper (P/p), Scissor (S/s) => ");
                playerSelection = scnr.next().toUpperCase().charAt(0);

                if (validSelections.indexOf(playerSelection) == -1){
                    System.out.println("\nInvalid Selection: Select Again");
                }
            }while (validSelections.indexOf(playerSelection) == -1);

            // Computers Random Choice
            computerSelection = playableSelection[random.nextInt(playableSelection.length)];


            System.out.println("Game: " + gamePlayed + " | Player choice: " + playerSelection + " - Computer choice: " + computerSelection);


            if (playerSelection == computerSelection) {
                System.out.println("Draw!");
                drawCounter++;
            } else if ((playerSelection == PAPER && computerSelection == ROCK) ||
                    (playerSelection == ROCK && computerSelection == SCISSOR) ||
                    (playerSelection == SCISSOR && computerSelection == PAPER)
            ) {
                System.out.println("Player Wins!");
                playerWins++;
            } else {
                System.out.println("Computer Wins!");
                computerWins++;
            }

            System.out.println("Game "+ gamePlayed +" Results: | Player wins: " + playerWins + " vs. Player losses: " + computerWins + " vs. Draws: " + drawCounter+"\n");
            gamePlayed++;
        }
    }
}
