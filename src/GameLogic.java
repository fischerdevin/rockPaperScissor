import java.util.Random;

public class GameLogic {
    private int gamePlayed, totalRounds, wins, losses, draws;
    private final String validSelection = "RPS";
    private final Random random = new Random();

    public GameLogic(){
        gamePlayed = 1;
        totalRounds = 20;
        wins = 0;
        losses = 0;
        draws = 0;
    }

    public GameLogic(int totalRounds){
        gamePlayed = 1;
        this.totalRounds = totalRounds;
        wins = 0;
        losses = 0;
        draws = 0;
    }

    public int getGamePlayed(){
        return this.gamePlayed;
    }
    public int getTotalRounds(){
        return this.totalRounds;
    }
    public boolean isValid(char check){
        return validSelection.indexOf(check) == -1;
    }

    public void checkGame(char player){
        final char ROCK = 'R', PAPER = 'P', SCISSOR = 'S';
        char computer = validSelection.charAt(random.nextInt(validSelection.length()));
        System.out.println("Game: " + this.gamePlayed + " | Player choice : Computer Choice => " + player + " : " + computer);

        if (player == computer) {
            System.out.println("Draw!");
            this.draws++;
            gameStats();
        } else if (player == PAPER && computer == ROCK ||
                player == ROCK && computer == SCISSOR ||
                player == SCISSOR && computer == PAPER) {
            System.out.println("Player Wins!");
            this.wins++;
            gameStats();
        } else {
            System.out.println("Computer Wins!");
            this.losses++;
            gameStats();

        }
        this.gamePlayed++;
    }

    public void gameStats(){
        System.out.println("Game: " + this.gamePlayed + " | Results (Wins ,Losses ,Draws ): (" + this.wins + ", " + this.losses + ", " + this.draws + ")\n");
    }
    public void endGameStats(){
        System.out.println("\nEnd of the Rounds Stats: ");
        System.out.println("Total games played: "+ (this.gamePlayed - 1) + " | Results (Wins, Losses, Draws ): ("+ this.wins + ", " + this.losses + ", " + this.draws +")\n");
    }
}
