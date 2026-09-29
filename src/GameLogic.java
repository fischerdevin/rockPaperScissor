public class GameLogic {
    private int gamePlayed, totalRounds, wins, losses, draws;
    private final String validSelection = "RPS";
    public enum Result{
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
    public boolean isValid(char check){
        return validSelection.indexOf(check) != -1;
    }

    public Result checkGame(char player, char computer){
        if (player == computer) {
            return Result.DRAW;
        } else if (player == 'P' && computer == 'R'
                || player == 'R' && computer == 'S'
                || player == 'S' && computer == 'P') {
            return Result.WIN;
        } else {
            return Result.LOSS;
        }
    }

    public void playGame(Result result){
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
        gameStats();
        gamePlayed++;
    }

    public void gameStats(){
        System.out.println("Game: " + this.gamePlayed + " | Results (Wins ,Losses ,Draws ): (" + this.wins + ", " + this.losses + ", " + this.draws + ")\n");
    }
    public void endGameStats(){
        System.out.println("\nEnd of the Rounds Stats: ");
        System.out.println("Total games played: "+ (this.gamePlayed - 1) + " | Results (Wins, Losses, Draws ): ("+ this.wins + ", " + this.losses + ", " + this.draws +")\n");
    }
}
