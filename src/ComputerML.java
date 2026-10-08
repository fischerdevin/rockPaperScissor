import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class ComputerML implements Computer {

    private final String memoryFileName = "computer-ml-memory.txt";
    private final Random random = new Random();
    private final Queue<GameLogic.Selection> roundHistory = new LinkedList<>();
    private final Map<ArrayList<GameLogic.Selection>, Integer> roundMemory = new HashMap<>();    // stores the frequency of each past sequence of moves
    private final int roundHistoryCapacity = 5;

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    ComputerML() {
        importCurrentRoundHistory(memoryFileName);
    }

    @Override
    public GameLogic.Selection computerSelection(){

        if (roundHistory.size() == roundHistoryCapacity) {

            ArrayList<GameLogic.Selection> currentHistory = new ArrayList<>();

            // Generates an array for the last n - 1 rounds
            boolean skipFirst = true;
            for (GameLogic.Selection i : roundHistory) {

                if (skipFirst) {
                    skipFirst = false;
                } else {
                    currentHistory.add(i);
                } 
            }
            
            // The memory map is used to determine the closest matches from previous matches
            Set<ArrayList<GameLogic.Selection>> candidates = new HashSet<>();

            for (ArrayList<GameLogic.Selection> i : roundMemory.keySet()) {
                if ( i.subList(0, currentHistory.size()).equals(currentHistory) ) {
                    candidates.add(i);
                }
            }

            if (!candidates.isEmpty()) {

                // Based on the set of canidates, find the one with the largest frequency
                int maxFrequency = 0;
                GameLogic.Selection predictedSelection = null;

                for (ArrayList<GameLogic.Selection> i : candidates) {

                    System.out.println("\n");

                    if ( roundMemory.get(i) > maxFrequency ) {
                        maxFrequency = roundMemory.get(i);
                        predictedSelection = i.getLast();
                    }
                }
                return predictedSelection.next();

            }

        }

        // If no matching candidates are found, or if history is not large enough, fall back to random number generator
        GameLogic.Selection[] possibleSelections = GameLogic.Selection.values();
        GameLogic.Selection randomSelection = possibleSelections[random.nextInt(3)];
        return randomSelection;

    }

    @Override
    public void addToMemory(GameLogic.Selection selection) {

        roundHistory.add(selection);

        // If round history is beyond max capacity, pop and add current history to memory map
        if (roundHistory.size() > roundHistoryCapacity) {
            
            roundHistory.remove();

            ArrayList<GameLogic.Selection> currentHistory = new ArrayList<>();
            
            for (GameLogic.Selection i : roundHistory) {
                currentHistory.add(i);
            }

            // Increment round memory
            if (roundMemory.containsKey(currentHistory)) {
                roundMemory.put(currentHistory, roundMemory.get(currentHistory) + 1);
            } else {
                roundMemory.put(currentHistory, 1);
            }

            exportCurrentRoundHistory(memoryFileName, currentHistory);
        }

    }

    private void importCurrentRoundHistory(String fileName) {

        try (FileReader fr = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null) {
                
                String[] historyStringArray = line.split(",");
                ArrayList<GameLogic.Selection> currentHistory = new ArrayList<>();

                for (String i : historyStringArray) {
                    currentHistory.add(GameLogic.Selection.valueOf(i));
                }

                // Increment round memory
                if (roundMemory.containsKey(currentHistory)) {
                    roundMemory.put(currentHistory, roundMemory.get(currentHistory) + 1);
                } else {
                    roundMemory.put(currentHistory, 1);
                }
                
            }
            
        } catch (IOException e) {
            // Convert to unchecked exception to keep method signatures clean
            throw new RuntimeException("Error reading memory file stream", e);
        }

    }

    private void exportCurrentRoundHistory(String fileName, ArrayList<GameLogic.Selection> currentHistory) {

        try (FileWriter fw = new FileWriter(fileName, true);
             PrintWriter writer = new PrintWriter(fw)) {

            String output = "";
            for (GameLogic.Selection i : currentHistory) {
                output += (i + ",");
            }
            output = output.substring(0, output.length() - 1);
            
            writer.println(output);
            writer.close();

        } catch (IOException e) {
            System.out.println("Error: Could not create or open machine-learning memory file");
            throw new RuntimeException("Error: Could not create or open machine-learning memory file");
        }

    }

}
