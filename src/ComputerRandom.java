import java.util.Random;

public class ComputerRandom implements Computer {

    private final Random random = new Random();

    @Override
    public GameLogic.Selection computerSelection(){

        GameLogic.Selection[] possibleSelections = GameLogic.Selection.values();
        return possibleSelections[random.nextInt(3)];

    }

}
