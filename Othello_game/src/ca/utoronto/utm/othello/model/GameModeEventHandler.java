package ca.utoronto.utm.othello.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;


public class GameModeEventHandler implements EventHandler<ActionEvent> {
	private Label gameModeLabel;
	private Othello othello;
	
	/**
	 *  GameModeEventHandler modifies the gameMode singleton 
	 *  based on the source of the ActionEvent
	 */
	public GameModeEventHandler(Label gameModeLabel, Othello othello) {
		this.gameModeLabel = gameModeLabel;
		this.othello = othello;
	}
	
	/**
	 *  Detect clicked event and change game mode
	 *  based on selected game mode.
	 */
	@Override
	public void handle(ActionEvent event) {
		GameModeButton source = (GameModeButton)event.getSource();
		String btnText = source.getText();
		this.gameModeLabel.setText(btnText);
		GameModeSingleton gameMode = GameModeSingleton.getInstance();
		gameMode.setGameMode(btnText);
		this.othello.switchOpponent();
	}
	
}
