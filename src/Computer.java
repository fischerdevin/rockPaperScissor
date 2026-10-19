public interface Computer {

    GameLogic.Selection computerSelection();

    default void recordRound(GameLogic.Selection player, GameLogic.Selection computer) {
        addToMemory(player);
    }

    default void addToMemory(GameLogic.Selection selection) {}

    default void saveMemory() {}

}
