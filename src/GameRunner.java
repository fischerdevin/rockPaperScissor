public class GameRunner {
    private final GameLogic game;
    private final PlayerInput input;
    private final Computer computer;

    public GameRunner(GameLogic game, PlayerInput input, Computer computer) {
        this.game = game;
        this.input = input;
        this.computer = computer;
    }

    public void play() {
        while (game.getGamesPlayed() <= game.getTotalRounds()) {
            GameLogic.Selection player = input.readSelection();
            GameLogic.Selection opponent = computer.computerSelection();
            GameLogic.Result result = game.playRound(player, opponent);

            System.out.println("Game: " + (game.getGamesPlayed() - 1)
                    + " | Player choice : Computer Choice => " + player + " : " + opponent);
            System.out.println(result + "!");
            System.out.println("Results (Wins, Losses, Draws): (" + game.getWins() + ", "
                    + game.getLosses() + ", " + game.getDraws() + ")\n");
            computer.recordRound(player, opponent);
        }

        // Learning data is written exactly once, after the game is complete.
        computer.saveMemory();
        System.out.println("End of the Rounds Stats:");
        System.out.println("Total games played: " + game.getTotalRounds()
                + " | Results (Wins, Losses, Draws): (" + game.getWins() + ", "
                + game.getLosses() + ", " + game.getDraws() + ")\n");
    }
}
