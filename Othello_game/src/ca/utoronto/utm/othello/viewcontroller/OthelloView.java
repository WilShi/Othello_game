package ca.utoronto.utm.othello.viewcontroller;
import java.io.File;
import ca.utoronto.utm.othello.model.BestMoveEventHandler;
import ca.utoronto.utm.othello.model.GameModeButton;
import ca.utoronto.utm.othello.model.GameModeEventHandler;
import ca.utoronto.utm.othello.model.Grid;
import ca.utoronto.utm.othello.model.GridEventHandler;
import ca.utoronto.utm.othello.model.Othello;
import ca.utoronto.utm.othello.model.PlayerTurnImage;
import ca.utoronto.utm.othello.model.ShowHintEventHandler;
import ca.utoronto.utm.othello.model.TimerSetter;
import ca.utoronto.utm.othello.model.TimerSetterEventHandler;
import ca.utoronto.utm.othello.model.TimerSingleton;
import ca.utoronto.utm.othello.model.TokenNumVBox;
import ca.utoronto.utm.othello.model.UndoButton;
import ca.utoronto.utm.othello.model.UndoButtonHandler;
import ca.utoronto.utm.othello.model.WinnerToken;
import ca.utoronto.utm.util.Observer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class OthelloView {
	Othello othello;
	public static final char EMPTY = ' ', P1 = 'X', P2 = 'O';

	OthelloView(Othello othello) {
		this.othello = othello;
	}
	
	public Scene createScene() {

		// Create a HBox and VBox to separate board and game information
		HBox rootHBox = new HBox();
		HBox leftHBox = new HBox();
		VBox leftMostVBox = new VBox();
		leftHBox.getChildren().add(leftMostVBox);
		VBox rightVBox = new VBox();
		rightVBox.setAlignment(Pos.CENTER);
		rootHBox.getChildren().add(leftHBox);
		rootHBox.getChildren().add(rightVBox);

		// Create Game information labels
		createGameInformationLabels(leftMostVBox);
		
		// CreateGameBoard
		createGameBoard(leftHBox);
		
		// Create a white space
		blankBox(rightVBox);
		// Create the image
		imageBox(rightVBox);
		// Create a white space
		blankBox(rightVBox);
		
		// Create game mode buttons
		createGameModeButtons(rightVBox);
		
		// Create blankBox
		blankBox(rightVBox);
		
		// Create timer
		createTimer(leftMostVBox);
		
		// Create Gif box
		gifBox(rightVBox);
		blankBox(rightVBox);
		
		// Create hint check box
		createHintBox(rightVBox);
		// Create a best move button
		createBestMoveButton(rightVBox);
		
		blankBox(rightVBox);
		
		// Create undo button
		createUndoButton(rightVBox);
		
		// Create restart button
		createRestartButton(rightVBox);
		
		
		this.othello.notifyObservers();
		return new Scene(rootHBox);
	}
	
	/**
	 * Create the logo display using an image in the rightBox
	 * @param rightBox
	 */
	private void imageBox(VBox rightVBox) {
	    Image imageDecline = new Image(new File("assets/logo.png").toURI().toString());
	    ImageView imageView = new ImageView(imageDecline);
	    imageView.setFitWidth(200);
	    imageView.setFitHeight(70);
	    rightVBox.getChildren().add(imageView);
	}
	
	/**
	 * Initialize the gif in the rightBox
	 * @param rightBox
	 */
	private void gifBox(VBox rightVBox) {
		Image imageDecline = new Image(new File("assets/tenor.gif").toURI().toString());
	    ImageView imageView = new ImageView(imageDecline);
	    imageView.setFitWidth(200);
	    imageView.setFitHeight(250);
	    rightVBox.getChildren().add(imageView);
	}
	
	/**
	 * add a whitespace for better visual
	 * @param rightBox
	 */
	private void blankBox(VBox rightVBox) {
		Rectangle box = new Rectangle();
		box.setFill(Color.TRANSPARENT);
		box.setStroke(Color.WHITE);
		box.setWidth(200);
		box.setHeight(22);
		rightVBox.getChildren().add(box);
	}
	
	/**
	 * Initialize the hint box
	 * @param rightBox
	 */
	private void createHintBox(VBox rightVBox) {
		HBox hintHBox = new HBox();
		hintHBox.setAlignment(Pos.CENTER);
		CheckBox showHint = new CheckBox();
		ShowHintEventHandler checkBoxEventHandler = new ShowHintEventHandler(othello);
		showHint.setOnAction(checkBoxEventHandler);
		Label showHintLabel = new Label("Show hint");
		hintHBox.getChildren().add(showHint);
		hintHBox.getChildren().add(showHintLabel);
		rightVBox.getChildren().add(hintHBox);
	}
	
	/**
	 * Initialize game information labels(the current player token, winner token)
	 * @param leftMostBox
	 */
	private void createGameInformationLabels(VBox leftMostVBox) {
		PlayerTurnImage playerTurnImage = new PlayerTurnImage();
		TokenNumVBox tokenNumImage = new TokenNumVBox();
		WinnerToken winnerToken = new WinnerToken();
		this.othello.attach(playerTurnImage);
		this.othello.attach(tokenNumImage);
		this.othello.attach(winnerToken);
		
		leftMostVBox.getChildren().add(playerTurnImage);
		leftMostVBox.getChildren().add(tokenNumImage);
		leftMostVBox.getChildren().add(winnerToken);
		leftMostVBox.setStyle("-fx-padding: 40px 20px;");
		leftMostVBox.setSpacing(20);
	}
	
	/**
	 * Create the gameMode buttons
	 * @param rightBox
	 */
	private void createGameModeButtons(VBox rightVBox) {
		Label gameModeLabel = new Label("Human VS Human");
		Label gameModeLabel0 = new Label("Game Mode:");
		gameModeLabel0.setFont(new Font("Arial", 20));
		GameModeEventHandler gameModeHandler = new GameModeEventHandler(gameModeLabel, this.othello);
		Label gameModeLabel2 = new Label("");
		
		GameModeButton button_hvh = createGameModeButton("Human VS Human", gameModeHandler);
		button_hvh.setMinSize(150, 40);
		GameModeButton button_hvr = createGameModeButton("Human VS Random", gameModeHandler);
		button_hvr.setMinSize(150, 40);
		GameModeButton button_hvg = createGameModeButton("Human VS Greedy", gameModeHandler);
		button_hvg.setMinSize(150, 40);
	
		rightVBox.getChildren().add(gameModeLabel0);
		rightVBox.getChildren().add(gameModeLabel);
		rightVBox.getChildren().add(gameModeLabel2);
		rightVBox.getChildren().add(button_hvh);
		rightVBox.getChildren().add(button_hvr);
		rightVBox.getChildren().add(button_hvg);
	}
	
	/**
	 * Create a game mode button using the text given 
	 * @param str
	 * @param GameModeEventHandler
	 */
	private GameModeButton createGameModeButton(String str, GameModeEventHandler handler) {
		GameModeButton gameModeButton = new GameModeButton();
		gameModeButton.setMinWidth(200);
		gameModeButton.setText(str);
		gameModeButton.setOnAction(handler);
		return gameModeButton;
	}
	
	/**
	 * Create a restart button
	 * @param rightBox
	 */
	private void createRestartButton(VBox rightVBox) {
		Button restartButton = new Button("Restart");
		restartButton.setLayoutY(100);
		
		restartButton.setMinWidth(100);
		rightVBox.getChildren().add(restartButton);
		restartButton.setOnAction(( ActionEvent ) -> {
			this.othello.restartGame();
		});
	}
	
	/**
	 * Create an undo button
	 * @param rightBox
	 */
	private void createUndoButton(VBox rightVBox) {
		UndoButtonHandler handler = new UndoButtonHandler(this.othello);
		Button UndoButton = new UndoButton();
		UndoButton.setMinWidth(100);
		rightVBox.getChildren().add(UndoButton);
		this.othello.attach((Observer)UndoButton);
		UndoButton.setOnAction(handler);
	}
	
	/**
	 * Create the timers
	 * @param leftMostBox
	 */
	private void createTimer(VBox leftMostVBox) {
		TimerSingleton timerSingleton = TimerSingleton.getInstance();
		this.othello.attach(timerSingleton);
		// Create timer setter
		HBox timerSetter = new HBox();
		TimerSetter minuteSetter = createTimerSetterTextField("5", timerSetter);
		Label gap = new Label(" : ");
		timerSetter.getChildren().add(gap);
		TimerSetter secondSetter = createTimerSetterTextField("00", timerSetter);
		leftMostVBox.getChildren().add(timerSetter);
		// Create timer Labels
		HBox timer1 = createTimerViewComponent(timerSingleton, P1, minuteSetter, secondSetter);
		HBox timer2 = createTimerViewComponent(timerSingleton, P2, minuteSetter, secondSetter);
		leftMostVBox.getChildren().add(timer1);
		leftMostVBox.getChildren().add(timer2);
	}
	
	/**
	 * Create the textfield for timer setter
	 * @param text
	 * @param timeSetter
	 */
	private TimerSetter createTimerSetterTextField(String text, HBox timerSetter) {
		TimerSetter timerSetterTextField = new TimerSetter(text);
		timerSetterTextField.setOnAction(new TimerSetterEventHandler());
		timerSetter.getChildren().add(timerSetterTextField);
		// model view hookup
		this.othello.attach(timerSetterTextField);
		return timerSetterTextField;
	}
	
	/**
	 * Initialize the view for timers
	 * @param timerSingleton
	 * @param player
	 * @param minuteSetter
	 * @param secondSetter
	 */
	private HBox createTimerViewComponent(TimerSingleton timerSingleton, char player, 
			TimerSetter minuteSetter, TimerSetter secondSetter) {
		HBox timer = new HBox();
		Label playerLabel;
		if (player == P1) {
			playerLabel = new Label("Player 1:  ");
		} else {
			playerLabel = new Label("Player 2:  ");
		}
		playerLabel.setStyle("-fx-font-size: 18px;");
		timer.getChildren().add(playerLabel);
		Label minute = new Label("5");
		minute.setStyle("-fx-font-size: 18px;");
		Label second = new Label("00");
		second.setStyle("-fx-font-size: 18px;");
		minuteSetter.addLabel(minute);
		secondSetter.addLabel(second);
		minute.setMaxWidth(50);
		second.setMaxWidth(50);
		Label gap = new Label(" : ");
		gap.setStyle("-fx-font-size:18px;");
		timer.getChildren().add(minute);
		timer.getChildren().add(gap);
		timer.getChildren().add(second);
		timerSingleton.setLabel(minute, second, player);
		return timer;
	}
	
	/**
	 * Create the gridPane which game would be played on
	 * @param leftBox
	 */
	private void createGameBoard(HBox leftHBox) {
		GridPane grid = new GridPane();
		leftHBox.getChildren().add(grid);
		GridEventHandler gridClickedEventHandler = new GridEventHandler(this.othello);
		Grid g;
		for(int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (row == 3 && col == 3) {
					Image imageDecline = new Image(new File("assets/TW.png").toURI().toString());
					g = new Grid(P1, row, col);
					g.setGraphic(new ImageView(imageDecline));
				} else if (row == 3 && col == 4) {
					Image imageDecline = new Image(new File("assets/TB.png").toURI().toString());
					g = new Grid(P2, row, col);
					g.setGraphic(new ImageView(imageDecline));
				} else if (row == 4 && col== 3) {
					Image imageDecline = new Image(new File("assets/TB.png").toURI().toString());
					g = new Grid(P2, row, col);
					g.setGraphic(new ImageView(imageDecline));
				} else if (row == 4 && col == 4) {
					Image imageDecline = new Image(new File("assets/TW.png").toURI().toString());
					g = new Grid(P1, row, col);
					g.setGraphic(new ImageView(imageDecline));
				} else {
					g = new Grid(EMPTY, row, col);
				}
				g.setMinHeight(90);
				g.setMinWidth(90);
				grid.add(g, col, row);
				// MODEL->VIEW hookup
				this.othello.attach(g);
				g.setOnAction(gridClickedEventHandler);
			}
		}
	}
	
	/**
	 * Initialize the BestMove button
	 * @param rightBox
	 */
	private void createBestMoveButton(VBox rightVBox) {
		Button bestMoveButton = new Button("Best Move");
		bestMoveButton.setOnAction(new BestMoveEventHandler(this.othello));
		bestMoveButton.setMinWidth(100);
		rightVBox.getChildren().add(bestMoveButton);
	}
}
