import java.util.Random;

public class Computer {
    private final String validSelection = "RPS";
    private final Random random = new Random();
    private char computerType = 'r';


    Computer(){

    }

    Computer(char type){
        computerType = type;
    }

    public char computerSelection(){
        return validSelection.charAt(random.nextInt(validSelection.length()));
    }


}
