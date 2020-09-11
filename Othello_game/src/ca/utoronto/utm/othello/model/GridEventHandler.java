package ca.utoronto.utm.othello.model;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import ca.utoronto.utm.othello.model.Othello;


public class GridEventHandler implements EventHandler<ActionEvent> {
	private Othello othello;
	
	/**
	 * GridEventHandler handles click event on grids.
	 */
	public GridEventHandler(Othello othello) {
		this.othello = othello;
	}
	
	/**
	 * Handles an actionEvent(a click) on a grid and makes a move at (row,col)
	 */
	@Override
	public void handle(ActionEvent event) {
		Grid source = (Grid)event.getSource();
		if (this.othello.move(source.row, source.col)) {;
		}
	}
}
