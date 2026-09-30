public class GameLogic {
    private int gamePlayed, totalRounds, wins, losses, draws;

    public enum Selection {
        ROCK, PAPER, SISSORS
    }

    public enum Result {
        WIN, LOSS, DRAW
    }

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

    public int getGamesPlayed(){
        return this.gamePlayed;
    }
    public int getTotalRounds(){
        return this.totalRounds;
    }

    public Result determineOutcome(Selection player, Selection opponent){

        if (player == opponent) {
            return Result.DRAW;
        }
        
        if (player == Selection.PAPER && opponent == Selection.ROCK
                || player == Selection.ROCK && opponent == Selection.SISSORS
                || player == Selection.SISSORS && opponent == Selection.PAPER) {
                    
            return Result.WIN;
        }
        
        return Result.LOSS;

    }

    public void playRound(Selection player, Selection opponent){

        System.out.println("Game: " + getGamesPlayed() + " | Player choice : Computer Choice => " + player + " : " + opponent);

        Result result = determineOutcome(player, opponent);

        switch (result){
            case WIN :
                System.out.println("Win!");
                wins++;
                break;

            case LOSS:
                System.out.println("Loss!");
                losses++;
                break;

            case DRAW:
                System.out.println("Draw!");
                draws++;
                break;
        }

        printGameStats();
        gamePlayed++;
    }

    public void printGameStats(){
        System.out.println("Results (Wins, Losses, Draws ): (" + this.wins + ", " + this.losses + ", " + this.draws + ")\n");
    }
    public void printEndGameStats(){
        System.out.println("\nEnd of the Rounds Stats: ");
        System.out.println("Total games played: "+ (this.gamePlayed - 1) + " | Results (Wins, Losses, Draws): ("+ this.wins + ", " + this.losses + ", " + this.draws +")\n");
    }
}
