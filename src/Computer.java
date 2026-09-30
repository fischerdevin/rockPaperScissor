import java.util.Random;

public class Computer {
    private final Random random = new Random();
    private char computerType = 'r';


    Computer(){

    }

    Computer(char type){
        computerType = type;
    }

    public GameLogic.Selection computerSelection(){

        GameLogic.Selection[] possibleSelections = GameLogic.Selection.values();

        return possibleSelections[random.nextInt(3)];

    }


}
