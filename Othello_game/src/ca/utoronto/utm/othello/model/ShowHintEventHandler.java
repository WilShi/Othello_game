package ca.utoronto.utm.othello.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

/**
 * Handler for a click onto the ShowHint checkbox
 */

public class ShowHintEventHandler implements EventHandler<ActionEvent>{
	
	private Othello othello;
//	private Player player;
	private int x = 0, y = 0;
	public ShowHintEventHandler(Othello othello) {
		super();
		this.othello = othello;
	}
	
	/**
	 * shows the hint if the checkbox is checked
	 * @param event
	 */
	@Override
	public void handle(ActionEvent event) {
		CheckBox source = (CheckBox)event.getSource();
		if (source.isSelected() && !this.othello.isGameOver()) {
			this.othello.setShowHint(true);
			this.othello.setHintPosition(x, y);
			this.othello.notifyObservers();
		} else {
			this.othello.setShowHint(false);
			this.othello.notifyObservers();
		}
	}
}