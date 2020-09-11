package ca.utoronto.utm.othello.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *  Handler for the undo button, calls the undo method in othello and plays the click sound
 */
public class UndoButtonHandler implements EventHandler<ActionEvent> {
	private Othello othello;
	
	public UndoButtonHandler(Othello othello) {
		this.othello = othello;
	}

@Override
public void handle(ActionEvent event) {
	this.othello.undo();
};
}
