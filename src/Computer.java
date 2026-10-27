public interface Computer {

    GameLogic.Selection computerSelection();

    /** Returns the move the computer currently expects the human to play. */
    default GameLogic.Selection predictedHumanSelection() { return null; }

    default void recordRound(GameLogic.Selection player, GameLogic.Selection computer) {
        addToMemory(player);
    }

    default void addToMemory(GameLogic.Selection selection) {}

    default void saveMemory() {}

}
