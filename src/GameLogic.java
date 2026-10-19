public class GameLogic {
    private int gamePlayed, totalRounds, wins, losses, draws;

    public enum Selection {
        ROCK, PAPER, SCISSORS;

        public Selection next() {   // guaranteed to return dominant selection
            return values()[(this.ordinal() + 1) % values().length];
        }
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
                || player == Selection.ROCK && opponent == Selection.SCISSORS
                || player == Selection.SCISSORS && opponent == Selection.PAPER) {
                    
            return Result.WIN;
        }
        
        return Result.LOSS;

    }

    public Result playRound(Selection player, Selection opponent){
        Result result = determineOutcome(player, opponent);

        switch (result){
            case WIN :
                wins++;
                break;

            case LOSS:
                losses++;
                break;

            case DRAW:
                draws++;
                break;
        }

        gamePlayed++;
        return result;
    }

    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getDraws() { return draws; }
}
