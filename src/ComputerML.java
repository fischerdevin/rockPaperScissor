import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ComputerML implements Computer {
    private static final String MEMORY_FILE_NAME = "computer-ml-memory.txt";
    private static final int HISTORY_LENGTH = 5;

    private final Random random = new Random();
    private final Deque<GameLogic.Selection> recentMoves = new ArrayDeque<>();
    private final Map<List<GameLogic.Selection>, Integer> roundMemory = new HashMap<>();
    private final Difficulty difficulty;

    public enum Difficulty { EASY, MEDIUM, HARD }

    public ComputerML() { this(Difficulty.HARD); }

    public ComputerML(Difficulty difficulty) {
        this.difficulty = difficulty;
        importMemory(MEMORY_FILE_NAME);
    }

    @Override
    public GameLogic.Selection computerSelection() {
        if (difficulty != Difficulty.EASY && recentMoves.size() >= HISTORY_LENGTH - 1) {
            List<GameLogic.Selection> prefix = new ArrayList<>(recentMoves);
            List<GameLogic.Selection> bestSequence = null;
            int bestFrequency = -1;

            for (Map.Entry<List<GameLogic.Selection>, Integer> entry : roundMemory.entrySet()) {
                List<GameLogic.Selection> sequence = entry.getKey();
                if (sequence.size() == HISTORY_LENGTH
                        && sequence.subList(0, prefix.size()).equals(prefix)
                        && (entry.getValue() > bestFrequency
                        || (entry.getValue() == bestFrequency
                        && isEarlier(sequence, bestSequence)))) {
                    bestSequence = sequence;
                    bestFrequency = entry.getValue();
                }
            }

            if (bestSequence != null) {
                return bestSequence.get(HISTORY_LENGTH - 1).next();
            }
        }

        GameLogic.Selection[] choices = GameLogic.Selection.values();
        return choices[random.nextInt(choices.length)];
    }

    @Override
    public void recordRound(GameLogic.Selection player, GameLogic.Selection computer) {
        recentMoves.addLast(player);
        recentMoves.addLast(computer);

        while (recentMoves.size() >= HISTORY_LENGTH) {
            List<GameLogic.Selection> sequence = new ArrayList<>(recentMoves).subList(0, HISTORY_LENGTH);
            roundMemory.merge(new ArrayList<>(sequence), 1, Integer::sum);
            recentMoves.removeFirst();
            if (recentMoves.size() < HISTORY_LENGTH) {
                break;
            }
        }
    }

    @Override
    public void saveMemory() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MEMORY_FILE_NAME, false))) {
            for (Map.Entry<List<GameLogic.Selection>, Integer> entry : roundMemory.entrySet()) {
                writer.println(entry.getValue() + "|" + join(entry.getKey()));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not save machine-learning memory", e);
        }
    }

    private void importMemory(String fileName) {
        File memoryFile = new File(fileName);
        if (!memoryFile.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(memoryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 2);
                if (parts.length != 2) continue;
                roundMemory.put(parseSequence(parts[1]), Integer.parseInt(parts[0]));
            }
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("Could not read machine-learning memory", e);
        }
    }

    private static String join(List<GameLogic.Selection> sequence) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sequence.size(); i++) {
            if (i > 0) result.append(',');
            result.append(sequence.get(i));
        }
        return result.toString();
    }

    private static boolean isEarlier(List<GameLogic.Selection> first,
                                    List<GameLogic.Selection> second) {
        if (second == null) return true;
        for (int i = 0; i < first.size(); i++) {
            int comparison = Integer.compare(first.get(i).ordinal(), second.get(i).ordinal());
            if (comparison != 0) return comparison < 0;
        }
        return false;
    }

    private static List<GameLogic.Selection> parseSequence(String value) {
        List<GameLogic.Selection> sequence = new ArrayList<>();
        for (String move : value.split(",")) {
            sequence.add(GameLogic.Selection.valueOf(move));
        }
        return sequence;
    }
}
