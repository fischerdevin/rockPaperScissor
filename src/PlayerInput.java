import java.util.Scanner;

public class PlayerInput {
    private final Scanner scanner;

    public PlayerInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public Computer chooseComputer() {
        char choice;
        do {
            System.out.print("Choose Opponent: Machine Learning (M) or Random Selection (R) => ");
            choice = scanner.next().toUpperCase().charAt(0);
        } while (choice != 'M' && choice != 'R');

        if (choice == 'M') {
            System.out.println("Machine Learning Opponent Chosen\n");
            return new ComputerML(ComputerML.Difficulty.HARD);
        }
        System.out.println("Random Selection Opponent Chosen\n");
        return new ComputerRandom();
    }

    public GameLogic.Selection readSelection() {
        char choice;
        do {
            System.out.print("Choose: Rock (R/r), Paper (P/p), Scissor (S/s) => ");
            choice = scanner.next().toUpperCase().charAt(0);
            if (!Main.isValidSelection(choice)) {
                System.out.println("\nInvalid Selection: Select Again");
            }
        } while (!Main.isValidSelection(choice));
        return Main.charToSelection(choice);
    }
}
