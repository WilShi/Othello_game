package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.scene.control.Button;

public class UndoButton extends Button implements Observer {
	/**
	 * Create a new undo button, set the text to "undo" and disable it
	 */
	public UndoButton() {
		this.setText("Undo");
		this.setDisable(true);
	}
	@Override
	public void update(Observable o) {
		
		Othello othello = (Othello)o;
		/**
		 * Disable the button if there is only one board left in board
		 */
		if(othello.getBoards().size() == 1) {
			this.setDisable(true);
		}else {
			this.setDisable(false);
		}
	}

}
