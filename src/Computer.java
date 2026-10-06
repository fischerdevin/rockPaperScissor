import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class Computer {

    private final Random random = new Random();
    private final Queue<GameLogic.Selection> roundHistory = new LinkedList<>();
    private final Map<ArrayList<GameLogic.Selection>, Integer> roundMemoryMap = new HashMap<>();    // stores the frequency of each past sequence of moves
    private final int roundHistoryCapacity;

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    Computer() {
        this(Difficulty.MEDIUM);
    }

    Computer(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                roundHistoryCapacity = 3;
                break;
            case MEDIUM:
                roundHistoryCapacity = 5;
                break;
            case HARD:
                roundHistoryCapacity = 7;
                break;
            default:    // never occurs, but compiler freaks out without it
                roundHistoryCapacity = 0;
                break;
        }
    }

    public GameLogic.Selection computerSelection(){

        GameLogic.Selection[] possibleSelections = GameLogic.Selection.values();
        return possibleSelections[random.nextInt(3)];

    }

    public void addToMemory(GameLogic.Selection selection) {

        roundHistory.add(selection);

        // If round history is beyond max capacity, pop and add current history to memory map
        if (roundHistory.size() > roundHistoryCapacity) {

            roundHistory.remove();

            ArrayList<GameLogic.Selection> currentHistory = new ArrayList<>();
            for (GameLogic.Selection i : roundHistory) {
                currentHistory.add(i);
            }

            roundMemoryMap.put(currentHistory, roundMemoryMap.get(currentHistory));
        }

    }


}
