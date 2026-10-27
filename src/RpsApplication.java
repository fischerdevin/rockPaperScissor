import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class RpsApplication extends Application {
    private final Label roundLabel = new Label();
    private final Label humanChoiceLabel = new Label("Human choice: —");
    private final Label predictionLabel = new Label("Predicted human choice: —");
    private final Label computerChoiceLabel = new Label("Computer choice: —");
    private final Label winnerLabel = new Label("Winner: —");
    private final Label humanWinsLabel = new Label();
    private final Label computerWinsLabel = new Label();
    private final Label tiesLabel = new Label();
    private final Label statusLabel = new Label("Choose Rock, Paper, or Scissors to begin.");
    private final Button rockButton = new Button("Rock");
    private final Button paperButton = new Button("Paper");
    private final Button scissorsButton = new Button("Scissors");
    private final Button newGameButton = new Button("Start New Game");

    private GameLogic game;
    private Computer computer;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Rock Paper Scissors");
        Scene scene = new Scene(createRoot(stage), 680, 760);
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(700);
        startNewGame(20, ComputerML.Difficulty.HARD);
        stage.show();
    }

    private BorderPane createRoot(Stage stage) {
        MenuItem newGame = new MenuItem("Start a new game");
        newGame.setOnAction(event -> showNewGameDialog(stage));

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> stage.close());

        Menu gameMenu = new Menu("Game");
        gameMenu.getItems().addAll(newGame, exit);

        MenuItem about = new MenuItem("About");
        about.setOnAction(event -> showAboutDialog());
        Menu helpMenu = new Menu("Help");
        helpMenu.getItems().add(about);

        MenuBar menuBar = new MenuBar(gameMenu, helpMenu);

        Label humanHeading = heading("Human");
        Label computerHeading = heading("Computer");
        Label winnerHeading = heading("The winner");
        Label statisticsHeading = heading("Statistics");

        rockButton.setOnAction(event -> playRound(GameLogic.Selection.ROCK));
        paperButton.setOnAction(event -> playRound(GameLogic.Selection.PAPER));
        scissorsButton.setOnAction(event -> playRound(GameLogic.Selection.SCISSORS));
        newGameButton.setOnAction(event -> showNewGameDialog(stage));
        setNewGameButtonVisible(false);
        HBox choiceButtons = new HBox(10, rockButton, paperButton, scissorsButton);
        choiceButtons.setAlignment(Pos.CENTER);

        VBox content = new VBox(12, roundLabel, humanHeading, choiceButtons,
                humanChoiceLabel, computerHeading, predictionLabel,
                computerChoiceLabel, winnerHeading, winnerLabel,
                statisticsHeading, humanWinsLabel, computerWinsLabel, tiesLabel);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(24));

        ScrollPane contentScrollPane = new ScrollPane(content);
        contentScrollPane.setFitToWidth(true);
        contentScrollPane.setFitToHeight(true);
        contentScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        BorderPane root = new BorderPane(contentScrollPane);
        root.setTop(menuBar);
        VBox bottomBar = new VBox(6, newGameButton, statusLabel);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(8));
        root.setBottom(bottomBar);
        return root;
    }

    private Label heading(String text) {
        return new Label(text);
    }

    private void playRound(GameLogic.Selection humanChoice) {
        if (game.isComplete()) return;

        GameLogic.Selection prediction = computer.predictedHumanSelection();
        GameLogic.Selection computerChoice = computer.computerSelection();
        GameLogic.Result result = game.playRound(humanChoice, computerChoice);
        computer.recordRound(humanChoice, computerChoice);

        humanChoiceLabel.setText("Human choice: " + display(humanChoice));
        predictionLabel.setText("Predicted human choice: "
                + (prediction == null ? "No prediction yet" : display(prediction)));
        computerChoiceLabel.setText("Computer choice: " + display(computerChoice));
        winnerLabel.setText("Winner: " + winner(result));
        updateStatistics();

        if (game.isComplete()) {
            computer.saveMemory();
            disableChoiceButtons(true);
            setNewGameButtonVisible(true);
            statusLabel.setText("Game complete. Use Start new game to play again.");
        } else {
            roundLabel.setText("Round: " + game.getGamesPlayed() + " of " + game.getTotalRounds());
            statusLabel.setText("Choose a move for the next round.");
        }
    }

    private void startNewGame(int rounds, ComputerML.Difficulty difficulty) {
        if (computer != null) computer.saveMemory();
        game = new GameLogic(rounds);
        computer = new ComputerML(difficulty);
        roundLabel.setText("Round: 1 of " + rounds);
        humanChoiceLabel.setText("Human choice: ");
        predictionLabel.setText("Predicted human choice: ");
        computerChoiceLabel.setText("Computer choice: ");
        winnerLabel.setText("Winner: ");
        updateStatistics();
        disableChoiceButtons(false);
        setNewGameButtonVisible(false);
        statusLabel.setText("Choose Rock, Paper, or Scissors to begin.");
    }

    private void setNewGameButtonVisible(boolean visible) {
        newGameButton.setVisible(visible);
        newGameButton.setManaged(visible);
    }

    private void updateStatistics() {
        humanWinsLabel.setText("Human wins: " + game.getWins());
        computerWinsLabel.setText("Computer wins: " + game.getLosses());
        tiesLabel.setText("Ties: " + game.getDraws());
    }

    private void disableChoiceButtons(boolean disabled) {
        rockButton.setDisable(disabled);
        paperButton.setDisable(disabled);
        scissorsButton.setDisable(disabled);
    }

    private void showNewGameDialog(Stage owner) {
        TextInputDialog roundsDialog = new TextInputDialog(String.valueOf(game.getTotalRounds()));
        roundsDialog.initOwner(owner);
        roundsDialog.setTitle("Start a new game");
        roundsDialog.setHeaderText("Choose the number of rounds");
        roundsDialog.setContentText("Rounds (1–100):");

        Optional<String> result = roundsDialog.showAndWait();
        if (result.isEmpty()) return;

        try {
            int rounds = Integer.parseInt(result.get().trim());
            if (rounds < 1 || rounds > 100) throw new NumberFormatException();
            startNewGame(rounds, ComputerML.Difficulty.HARD);
        } catch (NumberFormatException exception) {
            showError("Rounds must be a whole number from 1 to 100.");
        }
    }

    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "A JavaFX Rock Paper Scissors game with a simple machine learning opponent.", ButtonType.OK);
        alert.setTitle("About Rock Paper Scissors");
        alert.setHeaderText("Rock Paper Scissors");
        alert.showAndWait();
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message, ButtonType.OK).showAndWait();
    }

    private static String display(GameLogic.Selection selection) {
        String text = selection.name().toLowerCase();
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }

    private static String winner(GameLogic.Result result) {
        switch (result) {
            case WIN: return "Human";
            case LOSS: return "Computer";
            default: return "Tie";
        }
    }
}
