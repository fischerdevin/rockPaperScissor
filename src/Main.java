import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        Application.launch(RpsApplication.class, args);
    }

    public static GameLogic.Selection charToSelection(char input) {
        switch (Character.toUpperCase(input)) {
            case 'R': return GameLogic.Selection.ROCK;
            case 'P': return GameLogic.Selection.PAPER;
            case 'S': return GameLogic.Selection.SCISSORS;
            default: throw new IllegalArgumentException("Invalid selection: " + input);
        }
    }

    public static boolean isValidSelection(char input) {
        char normalized = Character.toUpperCase(input);
        return normalized == 'R' || normalized == 'P' || normalized == 'S';
    }
}
